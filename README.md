# CSV to SAP Upload Utility

A Kotlin-based utility that converts CSV files to JSON format and uploads them to SAP.

## Description

This utility provides functionality to:
- Read CSV files and convert them to JSON format
- Upload JSON data directly to SAP systems
- Handle cycle count data processing

## Requirements

- Java SDK 17
- Kotlin 2.1
- Appropriate SAP system access credentials

## Features

- CSV to JSON conversion
- Direct SAP integration
- Configurable file paths for input/output
- Automated data processing

## Usage

1. **CSV to JSON Conversion**
   ```kotlin
   val csvPath = "path/to/your/input.csv"
   val jsonPath = "path/to/your/output.json"
   val savedPath = readCsvToRequestBody(csvPath, jsonPath)
   ```

2. **Upload to SAP**
   ```kotlin
   uploadJsonToSAP(jsonFilePath = "path/to/your/json")
   ```

## File Naming Convention

The system expects files in the following format:
- `PPC_cyclecount_YYYYMMDDHHMM.csv` for input CSV files
- `PPC_cyclecount_YYYYMMDDHHMM.json` for converted JSON files

## Setup

1. Clone the repository
2. Ensure you have Java SDK 17 installed
3. Configure your SAP connection parameters (refer to configuration documentation)
4. Build the project using your preferred build tool

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

- Abdelrahman Eid

## Last Updated

June 24, 2025

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
