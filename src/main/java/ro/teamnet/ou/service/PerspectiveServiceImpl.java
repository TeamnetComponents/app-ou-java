package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.ou.domain.Perspective;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;

import javax.inject.Inject;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public class PerspectiveServiceImpl extends AbstractServiceImpl<Perspective, Long> implements PerspectiveService {

    private PerspectiveRepository perspectiveRepository;

    @Inject
    public PerspectiveServiceImpl(PerspectiveRepository perspectiveRepository) {
        super(perspectiveRepository);
        this.perspectiveRepository = perspectiveRepository;
    }
}
