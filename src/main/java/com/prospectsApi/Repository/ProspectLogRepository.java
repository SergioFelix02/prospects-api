package com.prospectsApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prospectsApi.Model.ProspectLog;
import java.util.List;

@Repository
public interface ProspectLogRepository extends JpaRepository<ProspectLog, Long> {
    List<ProspectLog> findByIdProspect(Long idProspect);
}
