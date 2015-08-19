package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {


    private OrganizationalUnitRepository organizationalUnitRepository;

    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    public PerspectiveRepository perspectiveRepository;

    @Inject
    public OrganizationalUnitServiceImpl (OrganizationalUnitRepository organizationalUnitRepository, OrganizationalUnitNeoRepository organizationalUnitNeoRepository,
                                          PerspectiveRepository perspectiveRepository){
        this.organizationalUnitRepository = organizationalUnitRepository;
        this.organizationalUnitNeoRepository = organizationalUnitNeoRepository;
        this.perspectiveRepository = perspectiveRepository;
    }


//    @Override
//    public OrganizationalUnitDTO createOrganizationalUnitDTO(Long id, String code, String description, Date validFrom, Date validTo, PerspectiveDTO perspectiveDTO,
//                                                             OrganizationalUnitDTO parent, Set<OrganizationalUnitDTO> children) {
//        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();
//        organizationalUnitDTO.setId(id);
//        organizationalUnitDTO.setCode(code);
//        organizationalUnitDTO.setDescription(description);
//        organizationalUnitDTO.setValidFrom(validFrom);
//        organizationalUnitDTO.setValidTo(validTo);
//        organizationalUnitDTO.setPerspective(perspectiveDTO);
//        organizationalUnitDTO.setParent(parent);
//        organizationalUnitDTO.setChildren(children);
//
//        return organizationalUnitDTO;
//    }


    @Override
    @Transactional
    public OrganizationalUnitDTO findOrganizationalUnitById(Long id) {

        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findOne(id);

        return OrganizationalUnitMapper.toDTO(organizationalUnit,organizationalUnitNeo);
    }

    @Override
    @Transactional
    public Set<OrganizationalUnitDTO> getAllOrganizationalUnit() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findAll();
        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitsNeo = organizationalUnitNeoRepository.getAllOrganizationalUnits();

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if(organizationalUnits != null && organizationalUnitsNeo != null){
            for (OrganizationalUnit organizationalUnit : organizationalUnits) {
                for (ro.teamnet.ou.domain.neo.OrganizationalUnit neoOu : organizationalUnitsNeo) {
                    if(organizationalUnit.getId().equals(neoOu.getJpaId())){
                        organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit, neoOu));
                    }
                }
            }
        }

        return organizationalUnitDTOs;
    }

    @Override
    @Transactional
    public OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO) {

        OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toJPA(organizationalUnitDTO);
        organizationalUnitRepository.save(organizationalUnit);

        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = OrganizationalUnitMapper.toNeo(organizationalUnitDTO);
        organizationalUnitDTO.setId(organizationalUnit.getId());
        organizationalUnitNeoRepository.save(organizationalUnitNeo);

        return OrganizationalUnitMapper.toDTO(organizationalUnit, organizationalUnitNeo);
    }
    @Override
    public void delete(Long id) {
        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        organizationalUnitRepository.delete(organizationalUnit);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findByJpaId(id);
        organizationalUnitNeoRepository.delete(organizationalUnitNeo);
    }

}
