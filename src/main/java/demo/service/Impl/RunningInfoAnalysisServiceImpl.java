package demo.service.Impl;

import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInfoAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningInfoAnalysisServiceImpl implements RunningInfoAnalysisService {

    RunningInformationRepository runningInformationRepository;

    @Autowired
    public RunningInfoAnalysisServiceImpl(RunningInformationRepository runningInformationRepository) {
        this.runningInformationRepository = runningInformationRepository;
    }

    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformations) {
        return this.runningInformationRepository.save(runningInformations);
    }

    @Override
    public void deleteAll() {
        runningInformationRepository.deleteAll();
    }

    @Override
    public Page<RunningInformation> findByRunningId(String runningId, Pageable pageable) {
        return runningInformationRepository.findByRunningId(runningId, pageable);
    }

    @Override
    public void deleteByRunningId(String runningId) {
        runningInformationRepository.deleteByRunningId(runningId);
    }

    @Override
    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable) {
        return runningInformationRepository.findByHeartRateGreaterThan(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findByHeartRate(int heartRate, Pageable pageable) {
        return runningInformationRepository.findByHeartRate(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findAll(Pageable pageable) {
        return runningInformationRepository.findAll(pageable);
    }
}
