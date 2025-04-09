package carlo.delgado.cobol.retotecnico.service;
import carlo.delgado.cobol.retotecnico.document.Transaccion;
import reactor.core.publisher.Flux;

public interface TransDebCred {
    Flux<Transaccion> procesarCSV(String path);
    void imprimirReporte(Flux<Transaccion> transacciones);
}
