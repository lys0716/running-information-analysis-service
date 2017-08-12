package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RunningInformationRepository extends JpaRepository<RunningInformation, Long> {
    Page<RunningInformation> findByRunningId(@Param("runningId") String runningId, Pageable page);

    void deleteAll();

    @Transactional
    void deleteByRunningId(@Param("runningId") String runningId);
}
