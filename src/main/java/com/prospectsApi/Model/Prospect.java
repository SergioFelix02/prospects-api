package com.prospectsApi.Model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Prospect implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long idProspect;
    private String name;
    private String lastName;
    private String secondLastName;
    private String phoneNumber;
    private String status;

    public Prospect() {}

    public Prospect(Long idProspect, String name, String lastName, String secondLastName, String phoneNumber, String status) {
        this.idProspect = idProspect;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Long getIdProspect() {
        return idProspect;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setIdProspect(Long idProspect) {
        this.idProspect = idProspect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public void setPhoneNumber(String phoneNumber) {    
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prospect{" +
                "idProspect=" + idProspect +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", secondLastName='" + secondLastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
