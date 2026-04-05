# Distributed Cache

A small Java simulation of a sharded in-memory cache: consistent hashing–style node selection, per-node LRU eviction and TTL, optional range prefetching, request collapsing to limit duplicate backing-store reads, and add/remove node with redistribution.

## Features

- **Sharded cache nodes** — `DistributedCache` owns a list of `CacheNode` instances; `IDistributionPolicy` picks the node for each key (default: `hash(key) % nodeCount` via `ModDistribution`).
- **Backing store simulation** — `Database.fetch` models a slow or authoritative source; misses log `DB HIT` and populate the chosen node.
- **LRU eviction** — each node enforces a fixed capacity with `LRUEviction` tracking access order via a `LinkedHashMap` access-order map.
- **TTL expiry** — `CacheEntry` stores an expiry time; `get` drops expired entries and treats them as misses.
- **Request collapsing** — `RequestCollapser` allows at most one logical “open window” fetch per key within a configurable millisecond window (applies on cache miss before hitting the database).
- **Range prefetch** — `RangePrefetcher` loads neighboring integer keys (by `SimpleKey` / hash range) through `getInternal` to avoid recursive prefetch storms.
- **Scaling** — `addNode` / `removeNode` trigger `redistribute()`, which re-hashes all entries across the current node set.

## Design patterns

Uses **Strategy** (and small **Facade**-style orchestration) so policies stay swappable without changing the cache façade.

- **Strategy** — `IDistributionPolicy` (shard selection), `IEvictionPolicy` (eviction order), and `IPrefetcher` (what to warm after a miss) are interfaces; concrete implementations are injected or constructed inside `DistributedCache` / `CacheNode`.
- **Facade** — `DistributedCache` exposes `get`, scaling, and coordinates nodes, DB access, collapsing, and prefetch in one entry point.

## System concepts

Core pieces that model distribution, storage, and back-end access.

- **Distributed cache** — holds `List<CacheNode>`, applies `getNodeIndex(key, nodeCount)`, and on miss may consult `RequestCollapser`, `Database`, then `put` with TTL; optional prefetch via `IPrefetcher`.
- **Cache node** — local `Map<IKey, CacheEntry>` plus `IEvictionPolicy`; `get` refreshes LRU state and rejects expired entries.
- **Cache entry** — wraps key, value, expiry, and last-access metadata for TTL checks.
- **Redistribution** — on topology change, all entries are collected, nodes cleared, and each entry is re-inserted using the active distribution policy.

## Class diagram

![alt text](<Distributed Cache.png>)

## Usage

Typical flow: construct the cache with node count, per-node capacity, and collapse window; use `IKey` / `IValue` implementations (e.g. `SimpleKey`, `SimpleValue`); call `get` for reads.

- Build a `DistributedCache(nodeCount, capacityPerNode, windowMillis)` — e.g. `new DistributedCache(2, 2, 2000)`.
- Call `get(key)` to resolve from the correct shard, backing store on miss, with prefetch side effects on the default path.
- Call `getInternal(key)` from prefetchers to load without triggering another prefetch wave.
- Call `addNode()` / `removeNode()` to simulate cluster resize and full redistribution.

## Running

From this directory, compile and run the demo:

**Windows (PowerShell)**

```powershell
javac *.java
java Main
```

**Unix-style shell**

```sh
javac *.java
java Main
```

## Example output

Sample console output from `Main` (includes ~6s sleep for TTL). The two `Value_for_2` lines under “REQUEST COLLAPSING” are both cache hits in this demo; the collapser only runs on a **miss** and would log `Collapsed request for key: …` and return `null` if a second miss for the same key occurs inside the window.

```
---- CACHE MISS (DB HIT) ----
DB HIT for key: 1
DB HIT for key: -1
DB HIT for key: 0
DB HIT for key: 2
DB HIT for key: 3
Value_for_1

---- CACHE HIT ----
Value_for_1

---- REQUEST COLLAPSING ----
Value_for_2
Value_for_2

---- AFTER WINDOW (NO COLLAPSE) ----
Value_for_2

---- PREFETCH TEST ----
DB HIT for key: 10
DB HIT for key: 8
DB HIT for key: 9
DB HIT for key: 11
DB HIT for key: 12

---- LRU EVICTION TEST ----
DB HIT for key: 1
DB HIT for key: -1
DB HIT for key: 0
DB HIT for key: 2
DB HIT for key: 3

---- TTL EXPIRY TEST ----
DB HIT for key: 1
DB HIT for key: -1
DB HIT for key: 0
DB HIT for key: 2
DB HIT for key: 3
Value_for_1

---- SCALING TEST ----
Redistribution done.
Redistribution done.
```

## Key design principles

- **Separation of concerns** — routing and cross-cutting behavior (distribution, collapse, prefetch) live in `DistributedCache`; storage, eviction, and TTL handling stay inside `CacheNode` / `CacheEntry`.
- **Open for extension** — new shard rules, eviction algorithms, or prefetch strategies plug in via `IDistributionPolicy`, `IEvictionPolicy`, and `IPrefetcher`.
- **Loose coupling via interfaces** — `IKey` / `IValue` keep the cache agnostic of concrete key/value types; policies depend on small contracts instead of concrete nodes.
- **Explicit domain model** — `CacheEntry` makes expiry and metadata visible; redistribution is a clear, repeatable full re-hash over entries rather than implicit partial migration.