package rest.decrypto.dtos;

import lombok.Data;
import java.util.Set;

@Data
public class ComitenteDTO {
    private Long id;
    private String descripcion;
    private Set<MercadoSimpleDTO> mercados;
}