package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.mapper.PerspectiveMapper;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.PerspectiveNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class PerspectiveServiceImpl implements PerspectiveService {

    @Inject
    private PerspectiveRepository perspectiveRepository;
    @Inject
    private PerspectiveNeoRepository perspectiveNeoRepository;



//    @Override
//    public PerspectiveDTO createPerspectiveDTO(Long jpaId, Long neoId, String code, String description, OrganizationDTO organizationDTO, Set<OrganizationalUnitDTO> organizationalUnitDTOSet) {
//        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();
//        perspectiveDTO.setJpaId(jpaId);
//        perspectiveDTO.setNeoId(neoId);
//        perspectiveDTO.setCode(code);
//        perspectiveDTO.setDescription(description);
//        perspectiveDTO.setOrganization(organizationDTO);
//        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOSet);
//
//        return perspectiveDTO;
//    }

    @Override
    @Transactional
    public PerspectiveDTO findPerspectiveById(Long id) {

        Perspective perspective = perspectiveRepository.findOne(id);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveNeoRepository.findByJpaId(id);

        return PerspectiveMapper.toDTO(perspective, perspectiveNeo);
    }

    @Override
    @Transactional
    public Set<PerspectiveDTO> getAllPerspectives() {
        List<Perspective> perspectiveList = perspectiveRepository.findAll();
        List<ro.teamnet.ou.domain.neo.Perspective> perspectiveListNeo = perspectiveNeoRepository.getAllPerspectives();

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        if (perspectiveList != null && perspectiveListNeo != null) {
            for (int i = 0; i < perspectiveList.size(); i++) {
                for (int j=0; j < perspectiveListNeo.size(); j++) {
                    if (perspectiveList.get(i).getId().equals(perspectiveListNeo.get(j).getJpaId())) {
                        perspectiveDTOs.add(PerspectiveMapper.toDTO(perspectiveList.get(i), perspectiveListNeo.get(j)));
                    }
                }
            }
        }
        return perspectiveDTOs;
        }

    @Override
    @Transactional
    public PerspectiveDTO save(PerspectiveDTO perspectiveDTO) {

        Perspective perspective = PerspectiveMapper.toJPA(perspectiveDTO);
        perspectiveRepository.save(perspective);
        perspectiveDTO.setJpaId(perspective.getId());
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = PerspectiveMapper.toNeo(perspectiveDTO);
        perspectiveNeoRepository.save(perspectiveNeo);
        return PerspectiveMapper.toDTO(perspective, perspectiveNeo);
    }

    @Override
    public void delete(Long id) {
//        Perspective perspective = PerspectiveMapper.toJPA(perspectiveDTO);
//        perspectiveRepository.delete(perspective);
//
//        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = PerspectiveMapper.toNeo(perspectiveDTO);
//        perspectiveNeoRepository.delete(perspectiveNeo);

        Perspective perspective = perspectiveRepository.findOne(id);
        perspectiveRepository.delete(perspective);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveNeoRepository.findByJpaId(id);
        perspectiveNeoRepository.delete(perspectiveNeo);
    }

}
