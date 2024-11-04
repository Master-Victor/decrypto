package rest.decrypto.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Setter
@ToString(exclude = {"comitente", "mercado"})
@EqualsAndHashCode(exclude = {"comitente", "mercado"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Entidad intermedia que representa la relación entre Comitente y Mercado.")
public class ComitenteMercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la relación", example = "1", required = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comitente_id", nullable = false)
    //@JsonBackReference("comitente-mercados")
    @Schema(description = "El comitente asociado a esta relación")
    private Comitente comitente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mercado_id", nullable = false)
    //@JsonManagedReference("mercado-comitentes")
    @Schema(description = "El mercado asociado a esta relación")
    private Mercado mercado;
}
