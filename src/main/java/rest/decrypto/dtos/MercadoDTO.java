package rest.decrypto.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Set;

@Data
@Schema(description = "DTO para transferir datos de un Mercado")
public class MercadoDTO {
    private Long id;
    private String codigo;
    private String descripcion;
    private PaisDTO pais;
    private Set<ComitenteSimpleDTO> comitentes;
}