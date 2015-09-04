package ro.teamnet.ou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.repository.AccountRepository;
import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.mapper.AccountMapper;
import ro.teamnet.ou.mapper.OrganizationMapper;
import ro.teamnet.ou.repository.jpa.OrganizationRepository;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

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

    @PostConstruct
    public void synchronizeJpaAndNeoDbData() {
        syncAccounts();
        syncOrganizations();
        //TODO: sync everything else
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
            ro.teamnet.ou.domain.neo.Organization neoOrganization =  OrganizationMapper.toNeo(OrganizationMapper.toDTO(organization, true));

            ro.teamnet.ou.domain.neo.Organization existingNeoOrganization = organizationNeoRepository.findByJpaId(organization.getId());
            if (existingNeoOrganization != null) {
                neoOrganization.setId(existingNeoOrganization.getId());
            }
            organizationNeoRepository.save(neoOrganization);
        }
    }
}
