package carlo.delgado.cobol.retotecnico.repository;


import carlo.delgado.cobol.retotecnico.document.Transaccion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitoCreditoRepository extends ReactiveMongoRepository <Transaccion, String >{

}

