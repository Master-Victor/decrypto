package rest.decrypto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = "comitentes")
@EqualsAndHashCode(exclude = "comitentes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Entidad que representa un Mercado en el sistema.")
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del mercado", example = "1", required = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Código único del mercado", example = "MAE", required = true)
    private String codigo;

    @Column(nullable = false)
    @Schema(description = "Descripción del mercado", example = "Mercado de Acciones", required = true)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id", nullable = false)
    @Schema(description = "País al que pertenece el mercado")
    private Pais pais;

    @OneToMany(mappedBy = "mercado", fetch = FetchType.LAZY)
    //@JsonBackReference("mercado-comitentes")
    @Schema(description = "Lista de relaciones del mercado con diferentes comitentes")
    private Set<ComitenteMercado> comitentes;
}