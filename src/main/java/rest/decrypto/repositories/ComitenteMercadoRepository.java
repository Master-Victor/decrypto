package rest.decrypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.decrypto.models.ComitenteMercado;

public interface ComitenteMercadoRepository extends JpaRepository<ComitenteMercado, Long> {
    void deleteByComitenteId(Long id);

    void deleteByMercadoId(Long id);
}
