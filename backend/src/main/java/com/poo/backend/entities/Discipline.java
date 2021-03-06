package com.poo.backend.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "discipline")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String url;

    @ManyToOne
    private Department department;

    @OneToMany
    private List<RequisitesByCourse> requisites;

    public Discipline() {

    }

    public Discipline(String name, String code, String url) {
        this.name = name;
        this.code = code;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<RequisitesByCourse> getRequisites() {
        return requisites;
    }

    public void setRequisites(List<RequisitesByCourse> requisites) {
        this.requisites = requisites;
    }
}
