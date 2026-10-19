
# GoCycle

SENG 300 - Fall 2026  
Assignment 1: Requirements, Design, and Implementation

## Project Overview

GoCycle is a shared bike and scooter rental system developed in Java. The project focuses on applying SOLID principles and design patterns to create a flexible and maintainable system.

## Implemented Features

### 1. Pricing System - Strategy Pattern
- Supports casual, member, and student pricing.
- Calculates trip costs using an unlock fee and per-minute rate.
- Applies a surcharge for trips longer than 120 minutes.
- Allows new pricing strategies to be added easily.

### 2. Fault Reporting - Observer Pattern
- Supports vehicle fault reporting and notifications.
- Notifies relevant maintenance and control room components.
- Supports handling vehicles that are taken out of service.

## Project Structure

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
javac -d build src/pricing/*.java src/faults/*.java
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

These commands run the individual test programs. The project does not currently provide a single application entry point.

## Team Members

- Matthew Brydges - UCID: 30028443
- Raisa Rahman - UCID: 30239980
- Sourav Mondal - UCID: 30265422
- Ali Hazari - UCID: 10126902
- Dylan Dang - UCID: 30231021
