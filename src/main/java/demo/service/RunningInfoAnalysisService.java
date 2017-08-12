package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunningInfoAnalysisService {
    List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformations);

    void deleteAll();

    Page<RunningInformation> findByRunningId(String runningId, Pageable pageable);

    void deleteByRunningId(String runningId);
}
