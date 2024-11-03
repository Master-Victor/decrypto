package rest.decrypto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.decrypto.dtos.MercadoDTO;
import rest.decrypto.dtos.StatsDTO;
import rest.decrypto.models.Comitente;
import rest.decrypto.models.Mercado;
import rest.decrypto.models.Pais;
import rest.decrypto.repositories.ComitenteRepository;
import rest.decrypto.repositories.MercadoRepository;
import rest.decrypto.repositories.PaisRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private ComitenteRepository comitenteRepository;

    public List<Pais> getAllPaises() {
        return paisRepository.findAll();
    }

    public Pais getPaisById(Long id) {
        return paisRepository.findById(id).orElseThrow(() -> new RuntimeException("Pais no encontrado"));
    }

    public Pais createPais(Pais pais) {
        return paisRepository.save(pais);
    }

    public Pais updatePais(Long id, Pais pais) {
        pais.setId(id);
        return paisRepository.save(pais);
    }

    public void deletePais(Long id) {
        paisRepository.deleteById(id);
    }

    public List<StatsDTO> getStats() {
        List<Comitente> comitentes = comitenteRepository.findAllWithMercados();
        return StatsDTO.calculateMarketStats(comitentes);
    }
}
