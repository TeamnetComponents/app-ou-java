package ro.teamnet.ou.web.rest;

import ro.teamnet.ou.service.DbSynchronizationService;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/app/rest/dbSynchronization")
public class DbSynchronizationResource {

    private Boolean neoSyncInProgress = false;

    @Inject
    private DbSynchronizationService dbSynchronizationService;

    @RequestMapping(value = "/syncNeo", method = RequestMethod.POST)
    @Timed
    public ResponseEntity syncNeo() {
        if (!neoSyncInProgress) {
            neoSyncInProgress = true;
            dbSynchronizationService.synchronizeJpaAndNeoDbData();
            neoSyncInProgress = false;
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

}
