package rest.decrypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.decrypto.models.Comitente;

import java.util.List;

@Repository
public interface ComitenteRepository extends JpaRepository<Comitente, Long> {
    @Query("""
        SELECT DISTINCT c FROM Comitente c 
        LEFT JOIN FETCH c.mercados cm 
        LEFT JOIN FETCH cm.mercado m 
        LEFT JOIN FETCH m.pais
    """)
    List<Comitente> findAllWithMercados();
    @Query("""
        SELECT DISTINCT c FROM Comitente c 
        LEFT JOIN FETCH c.mercados cm 
        LEFT JOIN FETCH cm.mercado m 
        LEFT JOIN FETCH m.pais
        where c.id = :id
    """)
    List<Comitente> findAllWithMercadosById(Long id);
}