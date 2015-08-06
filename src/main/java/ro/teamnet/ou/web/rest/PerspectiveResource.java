package ro.teamnet.ou.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.service.PerspectiveService;

import javax.inject.Inject;

/**
 * Created by Radu.Hoaghe on 8/3/2015.
 */
@RestController
@RequestMapping("/app/rest/perspective")
public class PerspectiveResource extends ro.teamnet.bootstrap.web.rest.AbstractResource<Perspective,Long>{

    private PerspectiveService perspectiveService;

    @Inject
    public PerspectiveResource(PerspectiveService perspectiveService) {
        super(perspectiveService);
        this.perspectiveService = perspectiveService;
    }
}
