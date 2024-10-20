package com.prospectsApi.Model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class ProspectFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idProspectFile;
    private Long idProspect;
    private String name;
    private String path;
    private String mimetype;
    private String status;

    public ProspectFile() {}

    public ProspectFile(Long idProspectFile, Long idProspect, String name, String path, String mimetype, String status) {
        this.idProspectFile = idProspectFile;
        this.idProspect = idProspect;
        this.name = name;
        this.path = path;
        this.mimetype = mimetype;
        this.status = status;
    }

    public Long getIdProspectFile() {
        return idProspectFile;
    }

    public Long getIdProspect() {
        return idProspect;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getStatus() {
        return status;
    }

    public void setIdProspectFile(Long idProspectFile) {
        this.idProspectFile = idProspectFile;
    }

    public void setIdProspect(Long idProspect) {
        this.idProspect = idProspect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProspectFile{" +
                "idProspectFile=" + idProspectFile +
                ", idProspect=" + idProspect +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
