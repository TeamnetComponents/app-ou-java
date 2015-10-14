package ro.teamnet.ou.repository.jpa;

import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.ou.domain.jpa.Organization;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationRepository extends AppRepository<Organization, Long> {

    public Organization findByCode(String code);
}
