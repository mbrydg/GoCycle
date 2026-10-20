
# GoCycle

SENG 300 - Fall 2026  
Assignment 1: Requirements, Design, and Implementation

## Project Overview

GoCycle is a shared bike and scooter rental system developed in Java. The project focuses on applying SOLID principles and design patterns to create a flexible and maintainable system.

## Implemented Features

### 1. Vehicles, Docking, and Trips (`model`)
- Pedal bikes, electric bikes, and electric scooters, built on a common `Vehicle` base class
- Docking stations with docking points where vehicles are docked and released
- Electric vehicles can only be rented above a minimum battery level
- Riders hold a pricing strategy
- A trip is started and ended at docking points, and its cost is calculated when it ends.

### 2. Pricing System - Strategy Pattern
- Supports casual, member, and student pricing.
- Calculates trip costs using an unlock fee and per-minute rate.
- Applies a surcharge for trips longer than 120 minutes.
- Allows new pricing strategies to be added easily.

### 3. Fault Reporting - Observer Pattern
- Supports vehicle fault reporting and notifications.
- Notifies relevant maintenance and control room components.
- Supports handling vehicles that are taken out of service.

## Project Structure
- `src/model/` - Vehicles, docking stations and points, riders, trips, and model tests.
- `src/pricing/` - Pricing strategies, trip cost calculator, and pricing tests.
- `src/faults/` - Fault reporting, notification components, and fault tests.

## Requirements

- Java JDK
- Git (for cloning the repository)

## Build Instructions

Open a terminal in the GoCycle project folder.

Compile all Java source files:

```bash
mkdir -p build
javac -d build src/pricing/*.java src/faults/*.java src/model/*.java
```

## Run Instructions

Run the pricing tests:

```bash
java -cp build pricing.PricingTest
```

Run the fault-reporting tests:

```bash
java -cp build faults.FaultTest
```

Run the model tests:

```bash
java -cp build model.ModelTest
```

These commands run the individual test programs. The project does not currently provide a single application entry point.

## Team Members

- Matthew Brydges - UCID: 30028443
- Raisa Rahman - UCID: 30239980
- Sourav Mondal - UCID: 30265422
- Ali Hazari - UCID: 10126902
- Dylan Dang - UCID: 30231021
