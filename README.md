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
![Alt text](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=transacciones.drawio.png&dark=auto#R%3Cmxfile%3E%3Cdiagram%20name%3D%22P%C3%A1gina-1%22%20id%3D%2269q7_uZReN5ITKYdg43O%22%3EddG9DoMgEADgp2FXaNTO1talk0NnIlchQc8gjbZPXw1YS2wXcnx3cPwQlrfTxfBeXlGAJjQSE2EnQmmaJfO4wNPDgTpojBKO4g0q9QKPkdeHEjAEhRZRW9WHWGPXQW0D48bgGJbdUYdde97ADqqa673elLDSaUbTzUtQjVw7x8nRZVq%2BFvubDJILHL%2BIFYTlBtG6qJ1y0Mvbre%2Fi1p3%2FZD8HM9DZHwvmYNt7ngQfxIo3%3C%2Fdiagram%3E%3C%2Fmxfile%3E)
