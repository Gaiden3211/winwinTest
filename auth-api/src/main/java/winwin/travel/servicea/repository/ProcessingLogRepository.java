package winwin.travel.servicea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import winwin.travel.servicea.entity.ProcessingLog;

import java.util.UUID;

@Repository
public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}
