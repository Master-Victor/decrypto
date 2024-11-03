package rest.decrypto.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.decrypto.dtos.ComitenteSimpleDTO;
import rest.decrypto.dtos.MercadoDTO;
import rest.decrypto.dtos.PaisDTO;
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
public class MercadoService {

    @Autowired
    private MercadoRepository mercadoRepository;
    @Autowired
    private ComitenteRepository comitenteRepository;
    @Autowired
    private ComitenteMercadoRepository comitenteMercadoRepository;

    public List<MercadoDTO> getAllMercados() {
        List<Mercado> mercados = mercadoRepository.findAllWithMercados();
        return mercados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MercadoDTO getMercadoById(Long id) {
        Mercado mercado =  mercadoRepository.findByIdWithMercados(id);
        return this.convertToDTO(mercado);
    }

    public MercadoDTO createMercado(MercadoDTO mercadoDTO) {
        Mercado mercado = new Mercado();
        mercado.setCodigo(mercadoDTO.getCodigo());
        mercado.setDescripcion(mercadoDTO.getDescripcion());
        mercado.setPais(mercadoDTO.getPais().convertToEntity());

        Set<ComitenteMercado> comitenteMercados = new HashSet<>();

        Mercado guardado = mercadoRepository.save(mercado);
        for (ComitenteSimpleDTO comitenteSimpleDTO : mercadoDTO.getComitentes()) {
            Optional<Comitente> comitenteOpt = comitenteRepository.findById(comitenteSimpleDTO.getId());
            if (comitenteOpt.isPresent()) {
                ComitenteMercado comitenteMercado = new ComitenteMercado();
                comitenteMercado.setComitente(comitenteOpt.get());
                comitenteMercado.setMercado(guardado);
                comitenteMercadoRepository.save(comitenteMercado);
                comitenteMercados.add(comitenteMercado);
            } else {
                throw new IllegalArgumentException("Comitente con ID " + comitenteSimpleDTO.getId() + " no encontrado.");
            }
        }
        mercado.setComitentes(comitenteMercados);
        return this.convertToDTO(guardado);

    }
    @Transactional
    public MercadoDTO updateMercado(Long id, MercadoDTO mercadoDTO) {
        Mercado mercado = mercadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mercado con ID " + id + " no encontrado."));
        mercado.setCodigo(mercadoDTO.getCodigo());
        mercado.setDescripcion(mercadoDTO.getDescripcion());
        mercado.setPais(mercadoDTO.getPais().convertToEntity());
        Set<ComitenteMercado> comitenteMercados = new HashSet<>();

        comitenteMercadoRepository.deleteByMercadoId(id);

        Mercado guardado = mercadoRepository.save(mercado);
        for (ComitenteSimpleDTO comitenteSimpleDTO : mercadoDTO.getComitentes()) {
            Optional<Comitente> comitenteOpt = comitenteRepository.findById(comitenteSimpleDTO.getId());
            if (comitenteOpt.isPresent()) {
                ComitenteMercado comitenteMercado = new ComitenteMercado();
                comitenteMercado.setComitente(comitenteOpt.get());
                comitenteMercado.setMercado(guardado);
                comitenteMercadoRepository.save(comitenteMercado);
                comitenteMercados.add(comitenteMercado);
            } else {
                throw new IllegalArgumentException("Comitente con ID " + comitenteSimpleDTO.getId() + " no encontrado.");
            }
        }
        mercado.setComitentes(comitenteMercados);
        mercadoRepository.save(mercado);

        return convertToDTO(guardado);
    }
    @Transactional
    public void deleteMercado(Long id) {
        comitenteMercadoRepository.deleteByMercadoId(id);
        mercadoRepository.deleteById(id);
    }

    private MercadoDTO convertToDTO(Mercado mercado) {
        MercadoDTO mercadoDTO = new MercadoDTO();
        mercadoDTO.setId(mercado.getId());
        mercadoDTO.setCodigo(mercado.getCodigo());
        mercadoDTO.setDescripcion(mercado.getDescripcion());

        // Convertir país a PaisDTO
        PaisDTO paisDTO = new PaisDTO();
        paisDTO.setId(mercado.getPais().getId());
        paisDTO.setNombre(mercado.getPais().getNombre());
        mercadoDTO.setPais(paisDTO);

        // Convertir comitentes a Set<ComitenteSimpleDTO>
        Set<ComitenteSimpleDTO> comitentesDTO = mercado.getComitentes().stream()
                .map(cm -> {
                    ComitenteSimpleDTO comitenteSimpleDTO = new ComitenteSimpleDTO();
                    comitenteSimpleDTO.setId(cm.getComitente().getId());
                    comitenteSimpleDTO.setDescripcion(cm.getComitente().getDescripcion());
                    return comitenteSimpleDTO;
                })
                .collect(Collectors.toSet());

        mercadoDTO.setComitentes(comitentesDTO);
        return mercadoDTO;
    }
}
