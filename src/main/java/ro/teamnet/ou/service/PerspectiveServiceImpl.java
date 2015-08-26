package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.mapper.PerspectiveMapper;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.PerspectiveNeoRepository;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.util.*;

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
        List<Perspective> perspectives = perspectiveRepository.findAll();
        Result<ro.teamnet.ou.domain.neo.Perspective> neoPerspectives = perspectiveNeoRepository.findAll();

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        for (Perspective perspective : perspectives) {
            for (ro.teamnet.ou.domain.neo.Perspective neoPerspective : neoPerspectives) {
                if (perspective.getId().equals(neoPerspective.getJpaId())) {
                    perspectiveDTOs.add(PerspectiveMapper.toDTO(perspective, neoPerspective));
                }
            }
        }
        return perspectiveDTOs;
    }

    @Override
    @Transactional
    public PerspectiveDTO save(PerspectiveDTO perspectiveDTO) {

        Perspective perspective = PerspectiveMapper.toJPA(perspectiveDTO);
        perspective = perspectiveRepository.save(perspective);
//        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = PerspectiveMapper.toNeo(perspectiveDTO);
//        perspectiveNeo.setJpaId(perspective.getId());
//        perspectiveNeo = perspectiveNeoRepository.save(perspectiveNeo);
        return PerspectiveMapper.toDTO(perspective);
    }

    public Set<PerspectiveDTO> findPerspectivesByOrganizationId(Long id){
         Set<Perspective> perspectives = perspectiveRepository.findByOrganizationId(id);
         Set<PerspectiveDTO> perspectiveDTOSet = new HashSet<>();
         for(Perspective perspective : perspectives){
            perspectiveDTOSet.add(PerspectiveMapper.toDTOLazy(perspective));
         }
         return perspectiveDTOSet;
    }

    @Override
    public void delete(Long id) {

        Perspective perspective = perspectiveRepository.findOne(id);
        perspectiveRepository.delete(perspective);
        ro.teamnet.ou.domain.neo.Perspective perspectiveNeo = perspectiveNeoRepository.findByJpaId(id);
        perspectiveNeoRepository.delete(perspectiveNeo);
    }

}
