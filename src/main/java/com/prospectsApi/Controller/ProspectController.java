package com.prospectsApi.Controller;

import com.prospectsApi.Model.Prospect;
import com.prospectsApi.Model.ProspectFile;
import com.prospectsApi.Model.ProspectLog;
import com.prospectsApi.Service.ProspectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/prospect")
public class ProspectController {
    private final ProspectService prospectService;

    public ProspectController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> getAllProspects(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            Page<Prospect> prospects = prospectService.findAllProspects(PageRequest.of(page, size));
            response.put("data", prospects);
            response.put("message", "Prospectos obtenidos con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al obtener la lista de prospectos: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> getProspectById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Prospect prospect = prospectService.findProspectById(id);
            List<ProspectFile> prospectFiles = prospectService.findFilesByProspectId(id);
            response.put("data", prospect);
            response.put("files", prospectFiles);
            response.put("message", "Prospecto encontrado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al encontrar el prospecto con ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveProspect(
        @RequestPart("prospect") Prospect newProspect, 
        @RequestPart("files") List<MultipartFile> files) {
        Map<String, Object> response = new HashMap<>();
        try {
            Prospect prospect = prospectService.saveProspect(newProspect, files);
            response.put("data", prospect);
            response.put("message", "Prospecto guardado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error al guardar el prospecto: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateProspect(@RequestBody Prospect updateProspect) {
        Map<String, Object> response = new HashMap<>();
        try {
            Prospect prospect = prospectService.updateProspect(updateProspect);
            response.put("data", prospect);
            response.put("message", "Prospecto actualizado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al actualizar el prospecto: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProspect(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            prospectService.deleteProspect(id);
            response.put("message", "Prospecto eliminado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar el prospecto con ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/file/preview/{idProspectFile}")
    public ResponseEntity<?> previewFile(@PathVariable("idProspectFile") Long idProspectFile) {
        try {
            ProspectFile file = prospectService.findFileById(idProspectFile);
            String filePath = System.getProperty("user.dir") + file.getPath();
            File previewFile = new File(filePath);

            if (!previewFile.exists()) {
                throw new IllegalArgumentException("El archivo no existe.");
            }

            String contentType = file.getMimetype();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(previewFile));

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + previewFile.getName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(previewFile.length())
                .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al cargar el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/logs/save")
    public ResponseEntity<Map<String, Object>> saveProspectLog(@RequestBody ProspectLog newProspectLog) {
        Map<String, Object> response = new HashMap<>();
        try {
            ProspectLog prospectLog = prospectService.saveProspectLog(newProspectLog);
            response.put("data", prospectLog);
            response.put("message", "Prospecto guardado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al guardar el prospecto: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logs/{idProspect}")
    public ResponseEntity<Map<String, Object>> getProspectLogsById(@PathVariable("idProspect") Long idProspect) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProspectLog> prospectLogs = prospectService.getProspectLogsById(idProspect);
            response.put("data", prospectLogs);
            response.put("message", "Logs del prospecto obtenidos con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al obtener los logs del prospecto: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
