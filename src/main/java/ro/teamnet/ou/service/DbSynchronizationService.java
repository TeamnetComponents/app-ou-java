package ro.teamnet.ou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.repository.AccountRepository;
import ro.teamnet.ou.domain.jpa.*;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.domain.neo.*;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.*;
import ro.teamnet.ou.repository.neo.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Marian.Spoiala on 9/3/2015.
 */
@Service
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
    private FunctionRepository functionRepository;

    @Inject
    private FunctionNeoRepository functionNeoRepository;

    @Inject
    private AccountFunctionRepository accountFunctionRepository;

    @PostConstruct
    public void synchronizeJpaAndNeoDbData() {
        syncAccounts();
        syncOrganizations();
        syncOrganizationalUnits();
        syncPerspectives();
        syncFunctionAccount();
    }

    private void syncAccounts() {
        for (Account accountJPA : accountRepository.findAll()) {
            log.debug("Creating Account in Neo : " + accountJPA.getLogin());
            ro.teamnet.ou.domain.neo.Account accountNeo = accountNeoRepository.findByJpaId(accountJPA.getId());
            if (accountNeo == null) {
                accountNeoRepository.save(AccountMapper.toNeo(accountJPA));
            } else if (!accountJPA.getLogin().equals(accountNeo.getUsername())) {
                accountNeo.setUsername(accountJPA.getLogin());
                accountNeoRepository.save(accountNeo);
            }
        }
    }

    private void syncOrganizations() {
        List<Organization> all = organizationRepository.findAll();
        for (Organization organization : all) {
            log.debug("Testing if Organizational is found in NeoDB : " + organization.getCode());
            ro.teamnet.ou.domain.neo.Organization neoOrganization = OrganizationMapper.toNeo(OrganizationMapper.toDTO(organization, true));

            ro.teamnet.ou.domain.neo.Organization existingNeoOrganization = organizationNeoRepository.findByJpaId(organization.getId());
            if (existingNeoOrganization != null) {
                neoOrganization.setId(existingNeoOrganization.getId());
            }
            organizationNeoRepository.save(neoOrganization);
        }
    }

    private void syncOrganizationalUnits() {
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

    private void syncPerspectives() {
        List<Perspective> all = perspectiveRepository.findAll();
        for (Perspective perspective : all) {
            log.debug("Testing if Perspective is found in NeoDB : " + perspective.getCode());
            ro.teamnet.ou.domain.neo.Perspective neoPerspective = new ro.teamnet.ou.domain.neo.Perspective();
            neoPerspective.setJpaId(perspective.getId());
            neoPerspective.setCode(perspective.getCode());
            neoPerspective.setOrganization(organizationNeoRepository.findByJpaId(perspective.getOrganization().getId()));
            neoPerspective.setOrganizationalUnit(organizationalUnitNeoRepository.findByJpaId(perspective.getOuTreeRoot().getId()));

            ro.teamnet.ou.domain.neo.Perspective existingPerspective = perspectiveNeoRepository.findByJpaId(perspective.getId());
            if (existingPerspective != null) {
                neoPerspective.setId(existingPerspective.getId());
            }
            perspectiveNeoRepository.save(neoPerspective);
        }
    }

    private void syncFunctionAccount() {
        List<Function> all = functionRepository.findAll();
        boolean checkIfIsUpdate = checkIfIsUpdate(), checkIfThisFunctionExist;
        for (Function function : all) {
            log.debug("Testing if Function is found in NeoDB : " + function.getCode());

            AccountFunction accountFunction = accountFunctionRepository.findAccountFunctionByFunctionId(function.getId());
            Account account = accountRepository.findOne(accountFunction.getAccount().getId());

            List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.getOrganizationalUnitByAccountFunctionId(accountFunction.getId());
            if (organizationalUnitList != null) {
                for (OrganizationalUnit organizationalUnit : organizationalUnitList) {

                    ro.teamnet.ou.domain.neo.Function neoFunction = prepareNeoFunction(function);
                    Set<ro.teamnet.ou.domain.neo.Function> existingFunction = functionNeoRepository.findByJpaIdOuIdAndAccountId(organizationalUnit.getId(), account.getId());
                    if (existingFunction.size() > 0) {
                        //if the OU have many links
                        for (ro.teamnet.ou.domain.neo.Function fct : existingFunction) {
                            if (function.getCode().equals(fct.getCode()) && function.getId().equals(fct.getJpaId())) {
                                //check if the link FUNCTION exists
                                neoFunction.setId(fct.getId());
                                saveNeoFunctionForFunctionAccount(organizationalUnit, account, neoFunction);
                                continue;
                            }
                            if (!checkIfIsUpdate) {
                                //in case of empty neo4j database
                                saveNeoFunctionForFunctionAccount(organizationalUnit, account, neoFunction);
                            } else {
                                //in case of missing some functions from neo4j database
                                checkIfThisFunctionExist = checkIfFunctionExist(existingFunction, function);
                                if(!checkIfThisFunctionExist) {
                                    saveNeoFunctionForFunctionAccount(organizationalUnit, account, neoFunction);
                                }
                            }
                        }
                    } else {
                        saveNeoFunctionForFunctionAccount(organizationalUnit, account, neoFunction);
                    }
                }
            }
        }
    }

    private boolean checkIfFunctionExist(Set<ro.teamnet.ou.domain.neo.Function> existingFunction, Function function) {
        for (ro.teamnet.ou.domain.neo.Function fct : existingFunction) {
            if (function.getCode().equals(fct.getCode()) && function.getId().equals(fct.getJpaId())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfIsUpdate() {
        return functionNeoRepository.findAll().iterator().hasNext();
    }

    private void saveNeoFunctionForFunctionAccount(OrganizationalUnit organizationalUnit, Account account, ro.teamnet.ou.domain.neo.Function neoFunction) {

        neoFunction.setOrganizationalUnit(organizationalUnitNeoRepository.findByJpaId(organizationalUnit.getId()));
        neoFunction.setAccount(accountNeoRepository.findByJpaId(account.getId()));
        functionNeoRepository.save(neoFunction);
    }

    private ro.teamnet.ou.domain.neo.Function prepareNeoFunction(Function function) {

        ro.teamnet.ou.domain.neo.Function neoFunction = new ro.teamnet.ou.domain.neo.Function();
        neoFunction.setJpaId(function.getId());
        neoFunction.setCode(function.getCode());

        return neoFunction;
    }
}
