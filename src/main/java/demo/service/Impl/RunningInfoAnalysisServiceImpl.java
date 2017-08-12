package demo.service.Impl;

import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInfoAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RunningInfoAnalysisServiceImpl implements RunningInfoAnalysisService {

    RunningInformationRepository runningInformationRepository;

    @Autowired
    public RunningInfoAnalysisServiceImpl(RunningInformationRepository runningInformationRepository) {
        this.runningInformationRepository = runningInformationRepository;
    }

    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformations) {
        int pressureUpperBound = 141;
        int hearRateBase = 60;
        Random random = new Random();
        for (RunningInformation runningInformation : runningInformations) {
            runningInformation.setHeartRate(hearRateBase + random.nextInt(pressureUpperBound));
            int heartRate = runningInformation.getHeartRate();
            if (heartRate <= 75) {
                runningInformation.setHealthWarningLevel(RunningInformation.HealthWarningLevel.Low);
            } else if (heartRate <= 120) {
                runningInformation.setHealthWarningLevel(RunningInformation.HealthWarningLevel.Normal);
            } else {
                runningInformation.setHealthWarningLevel(RunningInformation.HealthWarningLevel.High);
            }
            runningInformationRepository.save(runningInformation);
        }
        return runningInformations;
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
}
