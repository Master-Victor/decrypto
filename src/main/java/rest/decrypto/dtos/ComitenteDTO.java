package rest.decrypto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Set;

@Data
public class ComitenteDTO {
    private Long id;
    private String descripcion;
    //@JsonIgnore
    private Set<MercadoSimpleDTO> mercados;
}