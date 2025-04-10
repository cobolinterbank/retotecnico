package carlo.delgado.cobol.retotecnico.document;


import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
@Builder
public class Transaccion {


    @Id
    private Integer id;
    private String tipo;
    private double monto;


}
