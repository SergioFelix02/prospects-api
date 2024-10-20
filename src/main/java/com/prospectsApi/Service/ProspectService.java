package com.prospectsApi.Service;

import com.prospectsApi.Exception.ProspectNotFoundException;
import com.prospectsApi.Model.Prospect;
import com.prospectsApi.Model.ProspectFile;
import com.prospectsApi.Model.ProspectLog;
import com.prospectsApi.Repository.ProspectRepository;
import com.prospectsApi.Repository.ProspectFileRepository;
import com.prospectsApi.Repository.ProspectLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProspectService {
    private final ProspectRepository prospectRepository;
    private final ProspectFileRepository prospectFileRepository;
    private final ProspectLogRepository prospectLogRepository;
    @Autowired
    public ProspectService(
        ProspectRepository prospectRepository,
        ProspectFileRepository prospectFileRepository,
        ProspectLogRepository prospectLogRepository
        ) {
        this.prospectRepository = prospectRepository;
        this.prospectFileRepository = prospectFileRepository;
        this.prospectLogRepository = prospectLogRepository;
    }

    public Prospect saveProspect(Prospect prospect, List<MultipartFile> files) throws IOException {
        Prospect savedProspect = prospectRepository.save(prospect);

        if (files != null) {
            storeFiles(files, savedProspect);
        }

        return savedProspect;
    }

    private void storeFiles(List<MultipartFile> files, Prospect savedProspect) throws IOException {
        String uploadDirectory = System.getProperty("user.dir") + "/data/files/";
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String filePath = uploadDirectory + file.getOriginalFilename();
            String path = "/data/files/" + fileName;
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);
            // System.out.println("File uploaded: " + destinationFile.getPath());
            ProspectFile prospectFile = new ProspectFile();
            prospectFile.setIdProspect(savedProspect.getIdProspect());
            prospectFile.setName(fileName);
            prospectFile.setPath(path);
            prospectFile.setMimetype(file.getContentType());
            prospectFile.setStatus("1");
            prospectFileRepository.save(prospectFile);
        }
    }
    public Page<Prospect> findAllProspects(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("idProspect").descending());
        return prospectRepository.findAll(sortedByIdDesc);
    }

    public Prospect updateProspect(Prospect updateProspect) {
        Prospect existingProspect = findProspectById(updateProspect.getIdProspect());

        if (updateProspect.getName() == null) {
            updateProspect.setName(existingProspect.getName());
        }
        if (updateProspect.getLastName() == null) {
            updateProspect.setLastName(existingProspect.getLastName());
        }
        if (updateProspect.getSecondLastName() == null) {
            updateProspect.setSecondLastName(existingProspect.getSecondLastName());
        }
        if (updateProspect.getPhoneNumber() == null) {
            updateProspect.setPhoneNumber(existingProspect.getPhoneNumber());
        }
        if (updateProspect.getStatus() == null) {
            updateProspect.setStatus(existingProspect.getStatus());
        }

        return prospectRepository.save(updateProspect);
    }

    public Prospect findProspectById(Long id) {
        return prospectRepository.findById(id)
            .orElseThrow(()->new ProspectNotFoundException("Prospecto con ID: " + id + " no encontrado"));
    }

    public List<ProspectFile> findFilesByProspectId(Long idProspect) {
        return prospectFileRepository.findByIdProspect(idProspect);
    }
    
    public void deleteProspect(Long id) {
        prospectRepository.deleteById(id);
    }

    public ProspectFile findFileById(Long idProspectFile) {
        return prospectFileRepository.findById(idProspectFile)
            .orElseThrow(() -> new ProspectNotFoundException("Archivo con ID: " + idProspectFile + " no encontrado"));
    }    

    public ProspectLog saveProspectLog(ProspectLog prospectLog) throws IOException {
        return prospectLogRepository.save(prospectLog);
    }

    public List<ProspectLog> getProspectLogsById(Long idProspect) {
        return prospectLogRepository.findByIdProspect(idProspect);
    }

}
