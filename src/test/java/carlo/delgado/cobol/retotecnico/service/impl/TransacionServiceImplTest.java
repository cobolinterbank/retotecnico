package carlo.delgado.cobol.retotecnico.service.impl;
import carlo.delgado.cobol.retotecnico.document.Transaccion;
import carlo.delgado.cobol.retotecnico.repository.DebitoCreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

import java.util.List;
import static org.mockito.Mockito.*;


public class TransacionServiceImplTest {

    @Mock
    private DebitoCreditoRepository repository;

    @InjectMocks
    private TransacionServiceImpl service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void procesarCSV_deberiaProcesarCorrectamente(@TempDir Path tempDir) throws Exception {
        // Crear archivo CSV temporal
        File file = tempDir.resolve("test.csv").toFile();
        FileWriter writer = new FileWriter(file);
        writer.write("id,tipo,monto\n");
        writer.write("1,Crédito,100.50\n");
        writer.write("2,Débito,50.25\n");
        writer.close();

        // Mock del repository
        when(repository.save(any(Transaccion.class)))
                .thenAnswer(invocation -> {
                    Transaccion tx = invocation.getArgument(0);
                    return Flux.just(tx);
                });

        // Ejecutar
        Flux<Transaccion> resultado = service.procesarCSV(file.getAbsolutePath());

        // Verificar
        StepVerifier.create(resultado)
                .expectNextMatches(tx -> tx.getId() == 1 && tx.getTipo().equals("Crédito") && tx.getMonto() == 100.50)
                .expectNextMatches(tx -> tx.getId() == 2 && tx.getTipo().equals("Débito") && tx.getMonto() == 50.25)
                .verifyComplete();
    }

}
