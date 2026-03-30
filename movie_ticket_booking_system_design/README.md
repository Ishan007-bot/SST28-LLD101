# Movie Ticket Booking System

A small Java low-level design for cinema-style show booking with per-screen schedules, seat locking with expiry, pluggable pricing, and payments.

## Features

- Create bookings for a show with multiple seats; temporary seat locks with automatic expiry before re-use.
- Confirm bookings via interchangeable payment methods (UPI, debit card, credit card).
- Per-show pricing via strategy (e.g. base price vs demand-based multiplier).
- Admin adds shows while rejecting overlapping show times on the **same screen**, but allowing parallel shows on different screens.

## Design patterns

- **Strategy** — `IPricingStrategy` for seat/show pricing; `IPaymentStrategy` for how payment is executed.
- **Service layer** — `BookingService`, `SeatLockService`, and `AdminService` encapsulate booking, locking, and scheduling rules.
- **Template-style payment flow** — `Payment` delegates processing to the injected strategy.

## System concepts

- **Show & inventory** — A `Show` owns `ShowSeat` instances (wrapping physical `Seat`s), and now also tracks `duration` and `screenId`; prices are computed when the show is constructed.
- **Seat lifecycle** — Seats move through AVAILABLE → LOCKED (with timestamp and owner) → BOOKED, or back to AVAILABLE on failed payment or explicit unlock.
- **Lock expiry** — `SeatLockService` uses an in-memory lock map and `LOCK_TIME` to expire stale locks and free seats for new bookings.
- **Concurrency / consistency (single JVM)** — `SeatLockService` methods are `synchronized` to reduce double-booking in this single-process demo.
- **Show scheduling** — `AdminService` checks time-overlap **per screen** using `startTime + duration`; overlapping shows on the same screen are rejected.
- **Users** — `Customer` and `Admin` extend abstract `User`; booking and lock ownership reference `User`.

## Class diagram

![alt text](<Ticket Booking System.drawio.png>)

## Usage

Create a `Show` with `Movie`, a mutable `List<ShowSeat>`, `startTime`, `duration`, `screenId`, and an `IPricingStrategy`. Pass a `SeatLockService` into `BookingService`, then call `createBooking(user, show, seats)` and `confirmBooking(booking, paymentStrategy)` (e.g. `UpiPayment`, `DebitCardPayment`, `CreditCardPayment`). Use `AdminService.addShow(shows, newShow)` to maintain screen-wise schedules, preventing overlapping shows on the same screen while allowing different screens to run in parallel.

## Running

From the project directory:

```bash
javac *.java
java Main
```

## Example output

```text
=== USER1 BOOKING ===
Seats locked for user: Adam

=== USER2 TRY SAME SEAT (SHOULD FAIL) ===
Seats not available!

=== USER1 PAYMENT ===
Payment done via UPI: 300.0
Booking confirmed!

=== USER2 TRY AFTER BOOKED (SHOULD FAIL) ===
Seats not available!

=== LOCK EXPIRY TEST ===
Seats locked for user: Aryan
Waiting for lock to expire...
Trying again after expiry (SHOULD SUCCEED)
Seats locked for user: Adam

=== ADMIN SHOW TEST ===
Show added successfully
Time overlap on same screen! Cannot add show.
Show added successfully
Show added successfully
```

## Key design principles

- **Separation of concerns** — Domain objects (`Show`, `Booking`, `Seat`, `Movie`) stay thin; policies live in strategies and services.
- **Open for extension** — New pricing or payment behaviors add strategy implementations, not changes to core booking flow.
- **Explicit state** — Seat and booking status enums plus lock timestamps make invalid transitions easier to reason about and test.
- **Single responsibility** — Locking, payment, and scheduling rules are isolated in dedicated services (`SeatLockService`, `BookingService`, `AdminService`).