# File Integrity Monitor

A Java-based cybersecurity project that uses **SHA-256 hashing** to monitor file integrity and detect unauthorized or unexpected changes.

## About the Project

The File Integrity Monitor creates a baseline of monitored files by calculating and storing their SHA-256 hashes. When an integrity check is performed, the program compares the current hashes with the stored baseline to identify changes in the files.

This project demonstrates basic cybersecurity concepts, including cryptographic hashing, file handling, and integrity verification.

## Features

-  **SHA-256 Hashing** — Calculates a unique hash for each monitored file.
-  **Baseline Creation** — Stores the filenames and their original hashes.
-  **Unchanged Detection** — Identifies files whose contents remain the same.
-  **Modified Detection** — Detects changes in existing files.
-  **Missing File Detection** — Identifies files that have been deleted.
-  **New File Detection** — Identifies files added after the baseline was created.
-  **Integrity Summary** — Displays the number of unchanged, modified, missing, and new files.
-  **Input Validation** — Handles invalid menu input and checks for a missing baseline.

## Technologies Used

- **Programming Language:** Java
- **Hashing Algorithm:** SHA-256
- **Core Concepts:** File Handling, Cryptographic Hashing, Exception Handling, and Data Comparison
- **Development Environment:** Visual Studio Code

## Project Structure

```text
FileIntegrityMonitor/
├── FileIntegrityMonitor.java
└── monitored/
    ├── config.txt
    ├── users.txt
    └── settings.txt
```

> **Note:** The `baseline.txt` file is generated automatically when the program creates a baseline. It does not need to be included in the repository.

## How to Run

### Prerequisites

- Java Development Kit (JDK) installed.
- Visual Studio Code or another Java-compatible editor.

### Steps

**1. Download the project**

Download `FileIntegrityMonitor.java` and the `monitored` folder from this repository.

**2. Open the project folder**

Open the folder containing `FileIntegrityMonitor.java` and `monitored` in Visual Studio Code.

**3. Open the terminal**

Make sure the terminal is open in the project folder.

**4. Compile the Java program**

```bash
javac FileIntegrityMonitor.java
```

**5. Run the program**

```bash
java FileIntegrityMonitor
```

## Program Menu

When the program starts, it displays the following menu:

```text
=================================
      FILE INTEGRITY MONITOR
=================================

1. Create Baseline
2. Check Integrity
3. Exit
```

Choose an option by entering its corresponding number.

| Option | Description |
|---|---|
| `1` | Creates a baseline containing the SHA-256 hashes of monitored files. |
| `2` | Compares current files against the saved baseline. |
| `3` | Exits the program. |

## How It Works

1. **Create a baseline:** The program calculates the SHA-256 hash of every file in the `monitored` folder.
2. **Store file hashes:** The filenames and hashes are saved in `baseline.txt`.
3. **Check integrity:** The program calculates the current hashes and compares them with the stored hashes.
4. **Identify changes:** Each file is classified as `UNCHANGED`, `MODIFIED`, `MISSING`, or `NEW`.
5. **Display results:** The program prints an integrity summary showing the count for each status.

## Learning Outcomes

Through this project, I practised:

- Implementing SHA-256 hashing in Java.
- Reading and writing files using Java.
- Creating and comparing file integrity baselines.
- Detecting file changes using hash comparisons.
- Handling exceptions and validating user input.
- Applying fundamental cybersecurity concepts through programming.

## Disclaimer

This project was developed for **educational purposes** to practise Java programming and understand basic file integrity monitoring concepts. It is a learning project and is not intended to replace a production-grade security monitoring system.
