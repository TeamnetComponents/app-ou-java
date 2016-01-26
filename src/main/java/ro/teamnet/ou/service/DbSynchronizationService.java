package ro.teamnet.ou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.repository.AccountRepository;
import ro.teamnet.ou.domain.jpa.*;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marian.Spoiala on 9/3/2015.
 */
@Service
@Transactional
public class DbSynchronizationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private AccountNeoRepository accountNeoRepository;

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private OrganizationNeoRepository organizationNeoRepository;

    @Inject
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    @Inject
    private PerspectiveRepository perspectiveRepository;

    @Inject
    private PerspectiveNeoRepository perspectiveNeoRepository;

    @Inject
    private FunctionNeoRepository functionNeoRepository;

    @PostConstruct
    public void synchronizeJpaAndNeoDbData() {
        syncAccounts();
        syncOrganizations();
        syncOrganizationalUnits();
        syncPerspectives();
        syncFunctions();
    }

    /**
     * TODO Needs delete synchronization.
     */
    private void syncAccounts() {
        log.debug("Synchronizing JPA Accounts with Neo...");

        for (Account accountJPA : accountRepository.findAll()) {
            ro.teamnet.ou.domain.neo.Account accountNeo = accountNeoRepository.findByJpaId(accountJPA.getId());
            if (accountNeo == null) {
                log.debug("Creating Account in Neo : " + accountJPA.getLogin());
                accountNeoRepository.save(AccountMapper.toNeo(accountJPA));
            } else if (!accountJPA.getLogin().equals(accountNeo.getUsername())) {
                log.debug("Updating Account in Neo : " + accountJPA.getLogin());
                accountNeo.setUsername(accountJPA.getLogin());
                accountNeoRepository.save(accountNeo);
            }
        }
    }

    /**
     * TODO Needs delete synchronization.
     */
    private void syncOrganizations() {
        log.debug("Synchronizing JPA Organizations with Neo...");
        List<Organization> all = organizationRepository.findAll();

        for (Organization organization : all) {
            ro.teamnet.ou.domain.neo.Organization neoOrganization = OrganizationMapper.toNeo(OrganizationMapper.toDTO(organization, true));

            ro.teamnet.ou.domain.neo.Organization existingNeoOrganization = organizationNeoRepository.findByJpaId(organization.getId());
            if (existingNeoOrganization != null) {
                log.debug("Updating Organization in Neo : " + organization.getCode());
                neoOrganization.setId(existingNeoOrganization.getId());
            } else {
                log.debug("Creating Organization in Neo : " + organization.getCode());
            }
            organizationNeoRepository.save(neoOrganization);
        }
    }

    /**
     * TODO Needs delete synchronization.
     */
    /* Needs refactoring - what happens if parent organizational unit is null? If child OU is created before parent?*/
    private void syncOrganizationalUnits() {
        log.debug("Synchronizing JPA OrganizationalUnit with Neo...");
        List<OrganizationalUnit> all = organizationalUnitRepository.findAll();

        for (OrganizationalUnit organizationalUnit : all) {
            log.debug("Testing if Organizational Unit is found in NeoDB : " + organizationalUnit.getCode());

            ro.teamnet.ou.domain.neo.OrganizationalUnit neoOrganizationalUnit = OrganizationalUnitMapper.toNeo(OrganizationalUnitMapper.toDTO(organizationalUnit, true));
            if (organizationalUnit.getParent() != null) {
                neoOrganizationalUnit.setParent(organizationalUnitNeoRepository.findByJpaId(organizationalUnit.getParent().getId()));
            }

            ro.teamnet.ou.domain.neo.OrganizationalUnit existingNeoOrganizationalUnit = organizationalUnitNeoRepository.findByJpaId(organizationalUnit.getId());
            if (existingNeoOrganizationalUnit != null) {
                neoOrganizationalUnit.setId(existingNeoOrganizationalUnit.getId());
            }
            organizationalUnitNeoRepository.save(neoOrganizationalUnit);
        }
    }

    /**
     * TODO Needs delete synchronization.
     */
    private void syncPerspectives() {
        log.debug("Synchronizing JPA Perspective with Neo...");
        List<Perspective> all = perspectiveRepository.findAll();

        for (Perspective perspective : all) {
            ro.teamnet.ou.domain.neo.Perspective neoPerspective = new ro.teamnet.ou.domain.neo.Perspective();
            neoPerspective.setJpaId(perspective.getId());
            neoPerspective.setCode(perspective.getCode());
            neoPerspective.setOrganization(organizationNeoRepository.findByJpaId(perspective.getOrganization().getId()));
            neoPerspective.setOrganizationalUnit(organizationalUnitNeoRepository.findByJpaId(perspective.getOuTreeRoot().getId()));

            ro.teamnet.ou.domain.neo.Perspective existingPerspective = perspectiveNeoRepository.findByJpaId(perspective.getId());
            if (existingPerspective != null) {
                log.debug("Updating Perspective in Neo : " + perspective.getCode());
                neoPerspective.setId(existingPerspective.getId());
            } else {
                log.debug("Creating Perspective in Neo : " + perspective.getCode());
            }
            perspectiveNeoRepository.save(neoPerspective);
        }
    }

    /**
     * Synchronizes functions from JPA with functions from Neo.
     */
    void syncFunctions() {
        log.debug("Synchronizing JPA Functions with Neo...");
        Set<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.getAllWithAccountFunctions();

        for (OrganizationalUnit organizationalUnit : organizationalUnits) {
            ro.teamnet.ou.domain.neo.OrganizationalUnit neoOrganizationalUnit = organizationalUnitNeoRepository.findByJpaId(organizationalUnit.getId());
            Set<AccountFunction> accountFunctions = organizationalUnit.getAccountFunctions();
            Set<ro.teamnet.ou.domain.neo.Function> existingFunctions = functionNeoRepository.findByOrganizationalUnitJpaId(organizationalUnit.getId());

            Set<ro.teamnet.ou.domain.neo.Function> functionsToSave = new HashSet<>();
            for (AccountFunction accountFunction : accountFunctions) {
                Account account = accountFunction.getAccount();
                Function function = accountFunction.getFunction();

                Set<ro.teamnet.ou.domain.neo.Function> neoFunctions = functionNeoRepository.findByJpaIdOuIdAndAccountId(account.getId(), organizationalUnit.getId());
                for (ro.teamnet.ou.domain.neo.Function neoFunction : neoFunctions) {
                    if (neoFunction != null) {
                        if (!neoFunction.getCode().equals(function.getCode())) {
                            neoFunction = new ro.teamnet.ou.domain.neo.Function(neoFunction);
                            neoFunction.setCode(function.getCode());
                            functionsToSave.add(neoFunction);
                        }

                        existingFunctions.remove(neoFunction);
                    } else {
                        neoFunction = new ro.teamnet.ou.domain.neo.Function();
                        neoFunction.setId(null);
                        neoFunction.setJpaId(function.getId());
                        neoFunction.setCode(function.getCode());
                        neoFunction.setAccount(accountNeoRepository.findByJpaId(account.getId()));
                        neoFunction.setOrganizationalUnit(neoOrganizationalUnit);

                        functionsToSave.add(neoFunction);
                    }
                }
            }

            functionNeoRepository.delete(existingFunctions);
            functionNeoRepository.save(functionsToSave);
        }
    }
}
