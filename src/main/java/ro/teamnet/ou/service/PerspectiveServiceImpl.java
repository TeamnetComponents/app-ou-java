package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.domain.neo.Organization;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mapper.PerspectiveMapper;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.repository.neo.PerspectiveNeoRepository;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
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
    @Inject
    private OrganizationNeoRepository organizationNeoRepository;
    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    @Inject
    private OrganizationalUnitService organizationalUnitService;

    @Override
    public PerspectiveDTO findPerspectiveById(Long id) {

        Perspective perspective = perspectiveRepository.findOne(id);
       // ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveNeoRepository.findByJpaId(id);

        return PerspectiveMapper.toDTO(perspective);
    }

    @Override
    public Set<PerspectiveDTO> getAllPerspectives() {
        List<Perspective> perspectives = perspectiveRepository.findAll();
        Result<ro.teamnet.ou.domain.neo.Perspective> neoPerspectives = perspectiveNeoRepository.findAll();

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        for (Perspective perspective : perspectives) {
            for (ro.teamnet.ou.domain.neo.Perspective neoPerspective : neoPerspectives) {
                if (perspective.getId().equals(neoPerspective.getJpaId())) {
                    perspectiveDTOs.add(PerspectiveMapper.toDTO(perspective));
                }
            }
        }
        return perspectiveDTOs;
    }

    @Override
    public PerspectiveDTO save(PerspectiveDTO perspectiveDTO) {
        perspectiveDTO = saveJpa(perspectiveDTO);
        saveNeo(perspectiveDTO);
        return perspectiveDTO;
    }

    @Override
    public PerspectiveDTO update(PerspectiveDTO perspectiveDTO) {
        perspectiveDTO = updateJpa(perspectiveDTO);
        updateNeo(perspectiveDTO);
        return perspectiveDTO;
    }

    private void saveNeo(PerspectiveDTO perspectiveDTO) {
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = new ro.teamnet.ou.domain.neo.Perspective();
        perspectiveNeo.setJpaId(perspectiveDTO.getId());
        perspectiveNeo.setCode(perspectiveDTO.getCode());
        perspectiveNeo.setOrganization(organizationNeoRepository.findByJpaId(perspectiveDTO.getOrganization().getId()));
        perspectiveNeo.setOrganizationalUnit(organizationalUnitNeoRepository.findByJpaId(perspectiveDTO.getOuTreeRoot().getId()));
        perspectiveNeoRepository.save(perspectiveNeo);
    }

    private void updateNeo(PerspectiveDTO perspectiveDTO) {
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveNeoRepository.findByJpaId(perspectiveDTO.getId());
        perspectiveNeo.setJpaId(perspectiveDTO.getId());
        perspectiveNeo.setCode(perspectiveDTO.getCode());
        perspectiveNeoRepository.save(perspectiveNeo);
    }

    private PerspectiveDTO saveJpa(PerspectiveDTO perspectiveDTO) {
        Perspective perspective = perspectiveRepository.save(PerspectiveMapper.toJPA(perspectiveDTO));
        perspectiveDTO.setId(perspective.getId());
        perspectiveDTO.setOuTreeRoot(organizationalUnitService.saveOUTreeRoot(perspectiveDTO));
        return perspectiveDTO;
    }

    private PerspectiveDTO updateJpa(PerspectiveDTO perspectiveDTO) {
        Perspective perspectiveToSave = PerspectiveMapper.toJPA(perspectiveDTO);
        perspectiveToSave.setOuTreeRoot(perspectiveRepository.getOne(perspectiveDTO.getId()).getOuTreeRoot());
        Perspective perspective = perspectiveRepository.save(perspectiveToSave);
        perspectiveDTO.setId(perspective.getId());
        return perspectiveDTO;
    }

    public Set<PerspectiveDTO> findPerspectivesByOrganizationId(Long id){
         Set<Perspective> perspectives = perspectiveRepository.findByOrganizationId(id);
         Set<PerspectiveDTO> perspectiveDTOSet = new HashSet<>();
         for(Perspective perspective : perspectives){
            perspectiveDTOSet.add(PerspectiveMapper.toDTO(perspective, true));
         }
         return perspectiveDTOSet;
    }

    @Override
    public void delete(PerspectiveDTO perspectiveDTO) {
        Perspective perspective = perspectiveRepository.findOne(perspectiveDTO.getId());
        perspectiveRepository.delete(perspective);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit = organizationalUnitNeoRepository.findByJpaId(perspectiveDTO.getOuTreeRoot().getId());
        organizationalUnitNeoRepository.deleteNodeAndChildren(organizationalUnit.getId());
    }

}
