package demo.rest;

import demo.domain.RunningInformation;
import demo.service.RunningInfoAnalysisService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RunningBulkUploadRestController {

    private final String kDefaultPage = "0";
    private final String kDefaultItemPerPage = "30";
    private final String kFieldRunningId = "runningId";

    @Autowired
    RunningInfoAnalysisService runningInfoAnalysisService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInformation> runningInformations) {
        runningInfoAnalysisService.saveRunningInformation(runningInformations);
    }

    @RequestMapping(value = "/info/runningId/{runningId}", method = RequestMethod.GET)
    public Page<RunningInformation> findByRunningId(@PathVariable String runningId,
                                                    @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) int page,
                                                    @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) int size) {
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

    @RequestMapping(value = "/info/sortedRunningInfo", method = RequestMethod.GET)
    public Page<RunningInformation> findAllRunningInformationOrderByHealthLevel(@RequestParam(name = "page", required = false, defaultValue = kDefaultPage) int page) {
        final PageRequest pageRequest = new PageRequest(
                page, 2, Sort.Direction.DESC, "heartRate"
        );
        return runningInfoAnalysisService.findAll(pageRequest);
    }

    @RequestMapping(value = "/heartRate/{heartRate}", method = RequestMethod.GET)
    public Page<RunningInformation> findByHeartRate(@PathVariable int heartRate,
                                                    @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) int page,
                                                    @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) int size) {
        return runningInfoAnalysisService.findByHeartRate(heartRate, new PageRequest(page, size));
    }

    @RequestMapping(value = "/heartRateGreaterThan/{heartRate}", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findByHeartRateGreaterThan(@PathVariable int heartRate,
                                                                       @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) int page,
                                                                       @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) int size) {
        Page<RunningInformation> rawResults = runningInfoAnalysisService.findByHeartRateGreaterThan(heartRate, new PageRequest(page, size));
        List<RunningInformation> content = rawResults.getContent();

        List<JSONObject> results = new ArrayList<JSONObject>();
        for(RunningInformation runningInformation : content) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(kFieldRunningId, runningInformation.getRunningId());
            jsonObject.put("totalRunningTime", runningInformation.getTotalRunningTime());
            jsonObject.put("heartRate", runningInformation.getHeartRate());
            jsonObject.put("userId", runningInformation.getId());
            jsonObject.put("userName", runningInformation.getUserName());
            jsonObject.put("userAddress", runningInformation.getAddress());
            jsonObject.put("healthWarningLevel", runningInformation.getHealthWarningLevel());
            results.add(jsonObject);
        }
        return new ResponseEntity<List<JSONObject>>(results, HttpStatus.OK);
    }
}
