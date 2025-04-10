Lee un archivo CSV.

Procesa los datos.

Conecta a una base de datos MongoDB Atlas.

Guarda los datos leídos.
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

Link de diagrama de proceso:
![Alt text]([https://github.com/HACKATHON-DATA-ANALYTICS-2024-NTTDATA/eurekaserver/blob/master/src/main/resources/fotocreador/Arquitectura%20redneuronal.drawio.png]).
[English](README.md) / [日本語](README.ja.md)

