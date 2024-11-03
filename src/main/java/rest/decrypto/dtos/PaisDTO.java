package rest.decrypto.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import rest.decrypto.models.Pais;

@Data
@Schema(description = "DTO para transferir datos de un País")
public class PaisDTO {

    @Schema(description = "Identificador único del país", example = "1", required = true)
    private Long id;

    @Schema(description = "Nombre único del país", example = "Argentina", required = true)
    private String nombre;

    public Pais convertToEntity() {
        Pais pais = new Pais();
        pais.setId(this.id);
        pais.setNombre(this.nombre);
        return pais;
    }
}
