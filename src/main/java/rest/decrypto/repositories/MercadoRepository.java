package rest.decrypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.decrypto.models.Mercado;

import java.util.List;

public interface MercadoRepository extends JpaRepository<Mercado, Long> {

    @Query("""
        SELECT DISTINCT m FROM Mercado m
        LEFT JOIN FETCH m.pais 
        LEFT JOIN FETCH m.comitentes cm 
        LEFT JOIN FETCH cm.comitente
    """)
    List<Mercado> findAllWithMercados();
    @Query("""
        SELECT m FROM Mercado m
        LEFT JOIN FETCH m.pais 
        LEFT JOIN FETCH m.comitentes cm 
        LEFT JOIN FETCH cm.comitente
        WHERE m.id = :id
    """)
    Mercado findByIdWithMercados(Long id);
}