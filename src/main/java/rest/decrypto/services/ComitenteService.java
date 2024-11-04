package rest.decrypto.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.decrypto.dtos.ComitenteDTO;
import rest.decrypto.dtos.MercadoSimpleDTO;
import rest.decrypto.models.Comitente;
import rest.decrypto.models.ComitenteMercado;
import rest.decrypto.models.Mercado;
import rest.decrypto.repositories.ComitenteMercadoRepository;
import rest.decrypto.repositories.ComitenteRepository;
import rest.decrypto.repositories.MercadoRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComitenteService {

    @Autowired
    private ComitenteRepository comitenteRepository;
    @Autowired
    private MercadoRepository mercadoRepository;
    @Autowired
    private ComitenteMercadoRepository comitenteMercadoRepository;

    public List<ComitenteDTO> findAll() {
        List<Comitente> comitentes = comitenteRepository.findAllWithMercados();
        return comitentes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ComitenteDTO getComitenteById(Long id) {
        Comitente comitente = comitenteRepository.findAllWithMercadosById(id).get(0);
        return this.convertToDTO(comitente);
    }

    public ComitenteDTO createComitente(ComitenteDTO comitenteDTO) {
        Comitente comitente = new Comitente();
        comitente.setDescripcion(comitenteDTO.getDescripcion());

        Set<ComitenteMercado> comitenteMercados = new HashSet<>();

        Comitente guardado = comitenteRepository.save(comitente);
        for (MercadoSimpleDTO mercadoSimpleDTO : comitenteDTO.getMercados()) {
            Optional<Mercado> mercadoOpt = mercadoRepository.findById(mercadoSimpleDTO.getId());
            if (mercadoOpt.isPresent()) {
                ComitenteMercado comitenteMercado = new ComitenteMercado();
                comitenteMercado.setComitente(guardado);
                comitenteMercado.setMercado(mercadoOpt.get());
                comitenteMercadoRepository.save(comitenteMercado);
                comitenteMercados.add(comitenteMercado);
            } else {
                throw new IllegalArgumentException("Mercado con ID " + mercadoSimpleDTO.getId() + " no encontrado.");
            }
        }
        comitente.setMercados(comitenteMercados);
        return this.convertToDTO(guardado);
    }

    @Transactional
    public ComitenteDTO updateComitente(Long id, ComitenteDTO comitenteDTO) {
        Comitente comitente = comitenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comitente con ID " + id + " no encontrado."));
        comitente.setDescripcion(comitenteDTO.getDescripcion());
        Set<ComitenteMercado> comitenteMercados = new HashSet<>();

        comitenteMercadoRepository.deleteByComitenteId(id);

        for (MercadoSimpleDTO mercadoSimpleDTO : comitenteDTO.getMercados()) {
            Optional<Mercado> mercadoOpt = mercadoRepository.findById(mercadoSimpleDTO.getId());
            if (mercadoOpt.isPresent()) {
                ComitenteMercado comitenteMercado = new ComitenteMercado();
                comitenteMercado.setComitente(comitente);
                comitenteMercado.setMercado(mercadoOpt.get());
                comitenteMercadoRepository.save(comitenteMercado);
                comitenteMercados.add(comitenteMercado);
            } else {
                throw new IllegalArgumentException("Mercado con ID " + mercadoSimpleDTO.getId() + " no encontrado.");
            }
        }
        comitente.setMercados(comitenteMercados);
        comitenteRepository.save(comitente);

        return this.convertToDTO(comitente);
    }

    @Transactional
    public void deleteComitente(Long id) {
        comitenteMercadoRepository.deleteByComitenteId(id);
        comitenteRepository.deleteById(id);
    }

    private ComitenteDTO convertToDTO(Comitente comitente) {
        ComitenteDTO comitenteDTO = new ComitenteDTO();
        comitenteDTO.setId(comitente.getId());
        comitenteDTO.setDescripcion(comitente.getDescripcion());

        // Convertir mercados a Set<MercadoSimpleDTO>
        Set<MercadoSimpleDTO> mercadosDTO = comitente.getMercados().stream()
                .map(cm -> {
                    MercadoSimpleDTO mercadoDTO = new MercadoSimpleDTO();
                    mercadoDTO.setId(cm.getMercado().getId());
                    mercadoDTO.setCodigo(cm.getMercado().getCodigo());
                    mercadoDTO.setDescripcion(cm.getMercado().getDescripcion());
                    return mercadoDTO;
                })
                .collect(Collectors.toSet());

        comitenteDTO.setMercados(mercadosDTO);
        return comitenteDTO;
    }

}
