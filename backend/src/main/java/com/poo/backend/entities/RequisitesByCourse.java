package com.poo.backend.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "requisites_by_course")
public class RequisitesByCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseCode;

    @ManyToOne
    private Discipline discipline;

    @OneToMany
    private List<Requisite> requisites;

    public RequisitesByCourse() {

    }

    public RequisitesByCourse(String courseCode) {
        this.courseCode = courseCode;
    }

    public Long getId() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public List<Requisite> getRequisites() {
        return requisites;
    }

    public void setRequisites(List<Requisite> requisites) {
        this.requisites = requisites;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
