package com.prospectsApi.Model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class ProspectLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idProspectLog;
    private Long idProspect;
    private String note;
    private String status;

    public ProspectLog() {}

    public ProspectLog(Long idProspectLog, Long idProspect, String note, String status) {
        this.idProspectLog = idProspectLog;
        this.idProspect = idProspect;
        this.note = note;
        this.status = status;
    }

    public Long getIdProspectLog() {
        return idProspectLog;
    }

    public Long getIdProspect() {
        return idProspect;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public void setIdProspectLog(Long idProspectLog) {
        this.idProspectLog = idProspectLog;
    }

    public void setIdProspect(Long idProspect) {
        this.idProspect = idProspect;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProspectLog{" +
                "idProspectLog=" + idProspectLog +
                ", idProspect='" + idProspect + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
