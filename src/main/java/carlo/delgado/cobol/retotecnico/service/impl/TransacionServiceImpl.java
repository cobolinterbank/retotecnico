package carlo.delgado.cobol.retotecnico.service.impl;
import carlo.delgado.cobol.retotecnico.document.Transaccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacionServiceImpl {

    @Autowired
    private TransacionServiceImpl transactionRepository;

    public void processCsv(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<Transaction> transactions = new ArrayList<>();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals("id")) continue; // Ignorar la cabecera

                Transaction transaction = new Transaction();
                transaction.setId(Integer.parseInt(nextLine[0]));
                transaction.setTipo(nextLine[1]);
                transaction.setMonto(Double.parseDouble(nextLine[2]));
                transactions.add(transaction);
            }
            transactionRepository.saveAll(transactions).subscribe();
        }
    }

    public void generateReport() {
        transactionRepository.findAll()
                .collectList()
                .doOnTerminate(() -> System.out.println("Reporte Finalizado"))
                .doOnNext(transactions -> {
                    double totalBalance = transactions.stream().mapToDouble(Transaction::getMonto).sum();
                    Transaction highestTransaction = transactions.stream()
                            .max((t1, t2) -> Double.compare(t1.getMonto(), t2.getMonto()))
                            .orElse(null);

                    long countCredito = transactions.stream().filter(t -> t.getTipo().equals("Crédito")).count();
                    long countDebito = transactions.stream().filter(t -> t.getTipo().equals("Débito")).count();

                    System.out.println("Reporte de Transacciones");
                    System.out.println("---------------------------------------------");
                    System.out.println("Balance Final: " + totalBalance);
                    if (highestTransaction != null) {
                        System.out.println("Transacción de Mayor Monto: ID " + highestTransaction.getId() + " - " + highestTransaction.getMonto());
                    }
                    System.out.println("Conteo de Transacciones: Crédito: " + countCredito + " Débito: " + countDebito);
                })
                .subscribe();
    }

}
