package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class OUAccountServiceImpl implements OUAccountService {
    @Inject
    AccountNeoRepository neoRepository;

    @Override
    public List<Long> getOrganizationalUnitIds(Long accountId) {
        ro.teamnet.ou.domain.neo.Account neoAccount = neoRepository.findByJpaId(accountId);
        List<Long> ouIds = new ArrayList<>();
        if (neoAccount != null && neoAccount.getOrganizationalUnits() != null) {
            for (OrganizationalUnit organizationalUnit : neoAccount.getOrganizationalUnits()) {
                ouIds.add(organizationalUnit.getJpaId());
            }
        }
        return ouIds;
    }
}
