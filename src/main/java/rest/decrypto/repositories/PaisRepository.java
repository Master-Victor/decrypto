package rest.decrypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.decrypto.models.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {
}
