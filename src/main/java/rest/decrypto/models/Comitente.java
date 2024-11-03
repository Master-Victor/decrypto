package rest.decrypto.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = "mercados")
@EqualsAndHashCode(exclude = "mercados")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Entidad que representa un Comitente en el sistema.")
public class Comitente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del comitente", example = "1", required = true)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Descripción única del comitente", example = "Comitente ABC", required = true)
    private String descripcion;

    @OneToMany(mappedBy = "comitente", fetch = FetchType.LAZY)
    //@JsonManagedReference("comitente-mercados")
    @Schema(description = "Lista de relaciones del comitente con diferentes mercados")
    private Set<ComitenteMercado> mercados;

    public boolean getActivo() {
        return mercados.isEmpty();
    }
}