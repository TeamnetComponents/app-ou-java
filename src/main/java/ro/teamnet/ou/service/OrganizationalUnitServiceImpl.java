package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {
//extends AbstractServiceImpl<OrganizationalUnit, Long> implements OrganizationalUnitService {

    @Inject
    private OrganizationalUnitRepository organizationalUnitRepository;
    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;
    @Inject
    public PerspectiveRepository perspectiveRepository;

//    private final OrganizationalUnitRepository organizationalUnitRepository;
//    @Inject
//    public OrganizationalUnitServiceImpl(OrganizationalUnitRepository organizationalUnitRepository) {
//        super(organizationalUnitRepository);
//        this.organizationalUnitRepository = organizationalUnitRepository;
//    }

    @Override
    public OrganizationalUnitDTO createOrganizationalUnitDTO(Long id, String code, String description, Date validFrom, Date validTo, PerspectiveDTO perspectiveDTO,
                                                             OrganizationalUnitDTO parent, Set<OrganizationalUnitDTO> children) {
        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();
        organizationalUnitDTO.setId(id);
        organizationalUnitDTO.setCode(code);
        organizationalUnitDTO.setDescription(description);
        organizationalUnitDTO.setValidFrom(validFrom);
        organizationalUnitDTO.setValidTo(validTo);
        organizationalUnitDTO.setPerspective(perspectiveDTO);
        organizationalUnitDTO.setParent(parent);
        organizationalUnitDTO.setChildren(children);

        return organizationalUnitDTO;
    }

    @Override
    @Transactional
    public OrganizationalUnit updateOrganizationalUnit(OrganizationalUnit organizationalUnit, OrganizationalUnitDTO organizationalUnitDTO) {

        organizationalUnit = OrganizationalUnitMapper.from(organizationalUnitDTO);
        return organizationalUnitRepository.save(organizationalUnit);
    }

    @Override
    @Transactional
    public OrganizationalUnitDTO toOrganizationalUnitDTO(OrganizationalUnit organizationalUnit, ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo) {

        OrganizationalUnitDTO organizationalUnitDTO = OrganizationalUnitMapper.from(organizationalUnit, organizationalUnitNeo);
        return organizationalUnitDTO;
    }

    @Override
    @Transactional
    public ro.teamnet.ou.domain.neo.OrganizationalUnit updateOrganizationalUnitNeo(ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit, OrganizationalUnitDTO organizationalUnitDTO) {

        organizationalUnit = OrganizationalUnitMapper.fromNeo(organizationalUnitDTO);
        return organizationalUnitNeoRepository.save(organizationalUnit);
    }

    @Override
    @Transactional
    public OrganizationalUnit findOrganizationalUnitById(Long id) {
        return organizationalUnitRepository.findOne(id);
    }

    @Override
    @Transactional
    public ro.teamnet.ou.domain.neo.OrganizationalUnit findOrganizationalUnitNeoById(Long id) {
        return organizationalUnitNeoRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<OrganizationalUnit> getAllOrganizationalUnit() {
        return organizationalUnitRepository.findAll();
    }

    @Override
    @Transactional
    public List<ro.teamnet.ou.domain.neo.OrganizationalUnit> getAllOrganizationalUnitNeo() {
        Result<ro.teamnet.ou.domain.neo.OrganizationalUnit> result = organizationalUnitNeoRepository.findAll();
        List<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitList = new ArrayList<>();
        for (ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit : result) {
            organizationalUnitList.add(organizationalUnit);
        }
        return organizationalUnitList;
    }

    @Override
    @Transactional
    public OrganizationalUnitDTO create(OrganizationalUnitDTO organizationalUnitDTO) {

        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit = this.updateOrganizationalUnit(organizationalUnit, organizationalUnitDTO);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = new ro.teamnet.ou.domain.neo.OrganizationalUnit();
        organizationalUnitDTO.setJpaId(organizationalUnit.getId());
        organizationalUnitNeo = this.updateOrganizationalUnitNeo(organizationalUnitNeo, organizationalUnitDTO);

        return this.toOrganizationalUnitDTO(organizationalUnit, organizationalUnitNeo);
    }

    @Override
    @Transactional
    public OrganizationalUnitDTO update(OrganizationalUnit organizationalUnit, ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo, OrganizationalUnitDTO organizationalUnitDTO) {

        this.updateOrganizationalUnit(organizationalUnit, organizationalUnitDTO);
        this.updateOrganizationalUnitNeo(organizationalUnitNeo, organizationalUnitDTO);

        return this.toOrganizationalUnitDTO(organizationalUnit, organizationalUnitNeo);
    }
}
