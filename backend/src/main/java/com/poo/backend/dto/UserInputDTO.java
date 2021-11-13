package com.poo.backend.dto;


import java.util.List;
import java.util.stream.Collectors;

public class UserInputDTO {
    private final List<Long> departmentsId;
    private final List<String> keywords;
    private List<String> disciplinesCode;
    private String courseCode;

    public UserInputDTO(List<Integer> departmentsId, List<String> keywords) {
        this.departmentsId = createLongIds(departmentsId);
        this.keywords = keywords;
    }

    public UserInputDTO(List<Integer> departmentsId, List<String> disciplinesCode, List<String> keywords, String courseCode) {
        this.departmentsId = createLongIds(departmentsId);
        this.disciplinesCode = disciplinesCode;
        this.keywords = keywords;
        this.courseCode = courseCode;
    }

    private List<Long> createLongIds(List<Integer> ids) {
        return ids.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    public List<Long> getDepartmentsId() {
        return departmentsId;
    }

    public List<String> getDisciplinesCode() {
        return disciplinesCode;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
