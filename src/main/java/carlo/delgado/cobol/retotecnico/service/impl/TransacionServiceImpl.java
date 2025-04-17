package carlo.delgado.cobol.retotecnico.service.impl;

import carlo.delgado.cobol.retotecnico.document.Transaccion;
import carlo.delgado.cobol.retotecnico.repository.DebitoCreditoRepository;
import carlo.delgado.cobol.retotecnico.service.TransDebCred;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

import java.io.FileReader;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacionServiceImpl implements TransDebCred {
    private final DebitoCreditoRepository repository;

    @Override
    public Flux<Transaccion> procesarCSV(String path) {
        return Flux.defer(() -> {
            try (CSVReader reader = new CSVReader(new FileReader(path))) {
                List<String[]> rows = reader.readAll();
                return Flux.fromIterable(rows)
                        .skip(1) // omitir cabecera
                        .map(col -> Transaccion.builder()
                                .id(Integer.parseInt(col[0]))
                                .tipo(col[1])
                                .monto(Double.parseDouble(col[2]))
                                .build()
                        )
                        .flatMap(repository::save);
            } catch (Exception e) {
                log.error("Error al procesar el archivo CSV", e);
                return Flux.error(e);
            }
        });
    }

    @Override
    public void imprimirReporte(Flux<Transaccion> transacciones) {
        transacciones.collectList().subscribe(lista -> {
            double balance = lista.stream()
                    .mapToDouble(tx -> tx.getTipo().equalsIgnoreCase("Crédito") ? tx.getMonto() : -tx.getMonto())
                    .sum();

            Transaccion mayor = lista.stream()
                    .max(Comparator.comparing(Transaccion::getMonto))
                    .orElse(null);

            long creditos = lista.stream()
                    .filter(tx -> tx.getTipo().equalsIgnoreCase("Crédito"))
                    .count();

            long debitos = lista.stream()
                    .filter(tx -> tx.getTipo().equalsIgnoreCase("Débito"))
                    .count();

            log.info("Reporte de Transacciones");
            log.info("---------------------------------------------");
            log.info("Balance Final: {}", String.format("%.2f",balance));
            if (mayor != null) {
                log.info("Transacción de Mayor Monto: ID {} - {}", mayor.getId(), String.format("%.2f", mayor.getMonto()));
            }
            log.info("Conteo de Transacciones: Crédito: {} Débito: {}", creditos, debitos);









        });
    }


}
