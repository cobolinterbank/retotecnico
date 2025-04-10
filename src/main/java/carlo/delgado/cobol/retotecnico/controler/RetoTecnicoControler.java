package carlo.delgado.cobol.retotecnico.controler;

import carlo.delgado.cobol.retotecnico.service.TransDebCred;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reto")
public class RetoTecnicoControler {

    private final TransDebCred service;

    @GetMapping("/procesar")
    public Mono<String> procesarArchivo() {
        String path = "src/main/resources/data.csv";
        var transacciones = service.procesarCSV(path);
        service.imprimirReporte(transacciones);
        return Mono.just("Archivo procesado. Revisa la consola para el reporte.");
    }
}
