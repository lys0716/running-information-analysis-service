package demo.rest;

import demo.domain.RunningInformation;
import demo.service.RunningInfoAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningBulkUploadRestController {

    @Autowired
    RunningInfoAnalysisService runningInfoAnalysisService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInformation> runningInformations) {
        runningInfoAnalysisService.saveRunningInformation(runningInformations);
    }

    @RequestMapping(value = "/info/runningId/{runningId}", method = RequestMethod.GET)
    public Page<RunningInformation> findByRunningId(@PathVariable String runningId,
                                                    @RequestParam(name = "page") int page,
                                                    @RequestParam(name = "size") int size) {
        return runningInfoAnalysisService.findByRunningId(runningId, new PageRequest(page, size));
    }

    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void purge() {
        runningInfoAnalysisService.deleteAll();
    }

    @RequestMapping(value = "/info/runningId/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable String runningId) {
        runningInfoAnalysisService.deleteByRunningId(runningId);
    }
}
