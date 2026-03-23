# Pen System Design 🖋️

An extensible, object-oriented implementation of a multi-functional Pen system in Java. The design maps the physical behaviors of various pens into a software architecture using strict SOLID principles.

## System Architecture

* **Strategy Pattern (Multiple):** The core behaviors of the pen are abstracted into interfaces (`WriteStrategy`, `RefillStrategy`, `OpenCloseStrategy`). This heavily adheres to the **Open/Closed Principle**—adding a new "Marker" pen type or a "Twist" mechanism requires zero changes to the `Pen` class.
* **Factory Pattern:** The `PenFactory` simplifies the creation of complex object compositions. Clients only need to provide the type, color, and mechanism, and the factory injects the correct strategies.
* **State Management & Error Handling:** The `Pen` maintains an internal `isOpen` state. If a client attempts to execute `write()` before calling `start()`, an exception is thrown, enforcing correct usage flow.

## How to Run

Compile and execute all classes using standard Java commands in the root directory:

```bash
javac *.java
java Main