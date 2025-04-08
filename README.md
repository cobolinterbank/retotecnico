# retotecnico

+---------------------+
|     CSVController   |
+---------------------+
| - csvService: CSVService  |
+---------------------+
| +uploadCSV(file): ResponseEntity |
+---------------------+

            |
            v

+---------------------+
|     CSVService      |
+---------------------+
| - csvParser: CSVParser          |
| - repository: DataRepository    |
+---------------------+
| +processCSV(file): void         |
+---------------------+

            |
            v

+---------------------+
|     CSVParser       |
+---------------------+
| +parse(file): List<DataModel>  |
+---------------------+

            |
            v

+---------------------+
|     DataModel       |
+---------------------+
| - id: String        |
| - field1: String    |
| - field2: int       |
| ...                 |
+---------------------+

            |
            v

+--------------------------+
|   DataRepository         |
+--------------------------+
| extends MongoRepository  |
+--------------------------+
