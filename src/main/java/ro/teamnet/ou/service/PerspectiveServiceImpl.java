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
import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class PerspectiveServiceImpl implements PerspectiveService {
//extends AbstractServiceImpl<Perspective, Long> implements PerspectiveService {

    @Inject
    private PerspectiveRepository perspectiveRepository;
    @Inject
    private PerspectiveNeoRepository perspectiveNeoRepository;

//    @Inject
//    public PerspectiveServiceImpl(PerspectiveRepository perspectiveRepository) {
//        super(perspectiveRepository);
//        this.perspectiveRepository = perspectiveRepository;
//    }

    @Override
    public PerspectiveDTO createPerspectiveDTO(Long id, String code, String description, OrganizationDTO organizationDTO, Set<OrganizationalUnitDTO> organizationalUnitDTOSet) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();
        perspectiveDTO.setId(id);
        perspectiveDTO.setCode(code);
        perspectiveDTO.setDescription(description);
        perspectiveDTO.setOrganization(organizationDTO);
        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOSet);

        return perspectiveDTO;
    }

    @Override
    @Transactional
    public PerspectiveDTO toPerspectiveDTO(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {

        PerspectiveDTO perspectiveDTO = PerspectiveMapper.from(perspective, perspectiveNeo);
        return perspectiveDTO;
    }

    @Override
    @Transactional
    public Perspective updatePerspective(Perspective perspective, PerspectiveDTO perspectiveDTO) {

        perspective = PerspectiveMapper.from(perspectiveDTO);
        return perspectiveRepository.save(perspective);
    }

    @Override
    @Transactional
    public ro.teamnet.ou.domain.neo.Perspective updatePerspectiveNeo(ro.teamnet.ou.domain.neo.Perspective perspective, PerspectiveDTO perspectiveDTO) {

        perspective = PerspectiveMapper.fromNeo(perspectiveDTO);
        return perspectiveNeoRepository.save(perspective);
    }

    @Override
    @Transactional
    public Perspective findPerspectiveById(Long id) {
        return perspectiveRepository.findOne(id);
    }

    @Override
    @Transactional
    public ro.teamnet.ou.domain.neo.Perspective findPerspectiveNeoById(Long id) {
        return perspectiveNeoRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Perspective> getAllPerspectives() {
        return perspectiveRepository.findAll();
    }

    @Override
    @Transactional
    public List<ro.teamnet.ou.domain.neo.Perspective> getAllPerspectivesNeo() {
        Result<ro.teamnet.ou.domain.neo.Perspective> result = perspectiveNeoRepository.findAll();
        List<ro.teamnet.ou.domain.neo.Perspective> perspectiveList = new ArrayList<>();
        for (ro.teamnet.ou.domain.neo.Perspective perspective : result) {
            perspectiveList.add(perspective);
        }
        return perspectiveList;
    }

    @Override
    @Transactional
    public PerspectiveDTO create(PerspectiveDTO perspectiveDTO) {

        Perspective perspective = new Perspective();
        perspective = this.updatePerspective(perspective, perspectiveDTO);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = new ro.teamnet.ou.domain.neo.Perspective();
        perspectiveDTO.setJpaId(perspective.getId());
        perspectiveNeo = this.updatePerspectiveNeo(perspectiveNeo, perspectiveDTO);

        return this.toPerspectiveDTO(perspective, perspectiveNeo);
    }

    @Override
    @Transactional
    public PerspectiveDTO update(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo, PerspectiveDTO perspectiveDTO) {

        this.updatePerspective(perspective, perspectiveDTO);
        this.updatePerspectiveNeo(perspectiveNeo, perspectiveDTO);

        return this.toPerspectiveDTO(perspective, perspectiveNeo);
    }
}
