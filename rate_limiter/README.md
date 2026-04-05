# Rate Limiter

A small Java simulation that guards a downstream resource with pluggable rate-limit rules: a proxy decides allow/deny per call, and rejected work can be retried from a client queue.

## Features

- **Pluggable algorithms** — swap `FixedWindowRule` or `SlidingWindowRule` (or future rules) via `IRuleStrategy` without changing the proxy or resource.
- **Fixed window** — counts requests in non-overlapping time buckets; resets counter when `windowSize` elapses (`FixedWindowRule`).
- **Sliding window** — tracks request timestamps in a queue and evicts entries older than the window before deciding (`SlidingWindowRule`).
- **Proxy gate** — `RemoteResourceProxy.getResponse` calls `strategy.allowRequest()` before forwarding to `RemoteResource`.
- **Reject-and-retry demo** — `Main` polls a queue, re-enqueues on `RATE_LIMIT_EXCEEDED`, and sleeps one second between steps to stress the limiter.

## Design patterns

Uses **Strategy** and **Proxy** so policy and access control stay separate from the “real” service.

- **Strategy** — `IRuleStrategy.allowRequest()` encapsulates how quota is computed; `RemoteResourceProxy` delegates to the injected rule.
- **Proxy** — `RemoteResourceProxy` implements `IRateLimiter` like `RemoteResource` but adds rate limiting before delegation.

## System concepts

Core pieces model the client queue, the guarded API, and two classic limiter behaviors.

- **Rate limiter surface** — `IRateLimiter.getResponse(double)` is the uniform call path (proxy and resource both implement it).
- **Rule evaluation** — each `allowRequest()` consults time (`System.currentTimeMillis()`), window length, and max permits.
- **Fixed window state** — `windowStart` + `counter`; window rolls forward when elapsed time exceeds `windowSize`.
- **Sliding window state** — `Queue<Long>` of accept timestamps; old entries removed while `now - peek() > windowSize`.
- **Downstream resource** — `RemoteResource` only prints success; the proxy owns reject logging and exceptions.

## Class diagram

![alt text](<Rate Limiter.png>)

## Usage

Wire a strategy into the proxy and call `getResponse` like a normal client.

- Construct `RemoteResourceProxy` with `new SlidingWindowRule(maxRequests, windowSeconds)` or `new FixedWindowRule(maxRequests, windowSeconds)`.
- Call `getResponse(req)` on the proxy; on success the inner `RemoteResource` runs; on deny the proxy throws `RuntimeException("RATE_LIMIT_EXCEEDED")` after logging rejection.
- For retry semantics, catch that exception and re-queue the same request (as in `Main.processQueue`).

## Running

From this directory:

```bash
javac *.java
java Main
```

(On Windows PowerShell you can run the same two commands on separate lines.)

## Example output

Sample console output from `Main` (one second between steps; exact step counts can vary slightly with timing, but the allow/reject pattern is stable for the built-in queue and parameters).

```
===== Sliding Window Test =====

Step 1
Queue: [1.1, 2.1, 1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Processing request: 1.1
Request 1.1 served successfully

Step 2
Queue: [2.1, 1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Processing request: 2.1
Request 2.1 served successfully

Step 3
Queue: [1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Processing request: 1.2
Request 1.2 served successfully

Step 4
Queue: [1.3, 2.2, 1.4, 1.5, 2.3]
Request 1.3 REJECTED -> sending back to queue
Re-adding 1.3 to queue

...

All requests processed


===== Fixed Window Test =====

Step 1
Queue: [1.1, 2.1, 1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Processing request: 1.1
Request 1.1 served successfully

Step 2
Queue: [2.1, 1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Processing request: 2.1
Request 2.1 served successfully

Step 3
Queue: [1.2, 1.3, 2.2, 1.4, 1.5, 2.3]
Request 1.2 REJECTED -> sending back to queue
Re-adding 1.2 to queue

...

All requests processed
```

Sliding window uses **3 requests / 4 seconds**; fixed window uses **2 requests / 4 seconds**, so fixed rejects more often under the same paced workload.

## Key design principles

Keeps throttling policy interchangeable and the protected service unaware of limits.

- **Separation of concerns** — rules implement only `allowRequest()`; the proxy handles orchestration and exceptions; the resource handles “business” response.
- **Open for extension** — add token bucket, leaky bucket, or per-key limits by implementing `IRuleStrategy` and injecting into `RemoteResourceProxy`.
- **Uniform abstraction** — `IRateLimiter` lets callers treat proxy and resource similarly at the type level (useful for testing or stacking decorators).
- **Explicit failure** — rejection is visible via exception and log line, matching common API gateway / client retry patterns.