# Complex Number Calculator with Dynamic History

An interactive command-line application developed in Java, focused on complex number mathematics and advanced memory management. This project was awarded a grade of **19.1/20** in the Object-Oriented Programming (OOP) course.

## Key Features

* **Isolated Math Engine:** Complete logic for complex number operations (Addition, Subtraction, Multiplication, Division, Power, Negation, Conjugate, and Inverse).
* **Data Persistence (I/O):** Automatic saving and loading of the operation history via text files (`.txt`), bridging volatile memory (RAM) and permanent storage.
* **Cascading Recalculation Algorithm:** Advanced history management allowing users to edit, remove, move, or swap past operations. Altering a past record triggers a domino effect that automatically recalculates all subsequent results in memory.

## Applied OOP Concepts

* **Strict Encapsulation:** Object states are fully protected using `private` modifiers and accessed exclusively via Getter/Setter methods.
* **Modular Architecture:** The `Calculadora` class delegates storage responsibilities to the `Historico` class, which handles dynamic collections of `Operacao` objects.
* **Robust Error Handling:** Protection against invalid keyboard inputs (Data Misformatting) and mathematically impossible operations (Division by Zero).

## Technologies Used

* **Language:** Java
* **Core Components:** `ArrayList`, `Scanner`, `FileWriter`, and `File`

## How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/your-username/complex-number-calculator-java.git](https://github.com/your-username/complex-number-calculator-java.git)
