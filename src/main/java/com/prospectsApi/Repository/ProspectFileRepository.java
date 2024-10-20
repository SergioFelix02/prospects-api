package com.prospectsApi.Repository;

import com.prospectsApi.Model.ProspectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProspectFileRepository extends JpaRepository<ProspectFile, Long> {
    List<ProspectFile> findByIdProspect(Long idProspect);
}
