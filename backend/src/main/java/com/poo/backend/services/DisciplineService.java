package com.poo.backend.services;

import com.poo.backend.dto.DisciplineWithReqsDTO;
import com.poo.backend.dto.DisciplineWithoutReqsDTO;
import com.poo.backend.entities.Discipline;
import com.poo.backend.repositories.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepo;

    public List<DisciplineWithoutReqsDTO> findAll() {
        List<Discipline> disciplines = disciplineRepo.findAll();
        return disciplines.stream().map(DisciplineWithoutReqsDTO::new).collect(Collectors.toList());
    }

    public List<DisciplineWithoutReqsDTO> findAllByDepartmentsId(List<Long> departmentIds) {
        List<Discipline> disciplines = disciplineRepo.findAllByDepartmentIdIn(departmentIds);
        return disciplines.stream().map(DisciplineWithoutReqsDTO::new).collect(Collectors.toList());
    }

    public List<DisciplineWithReqsDTO> findAllWithRequisitesByDepartmentsId(List<Long> departmentIds) {
        List<Discipline> disciplines = disciplineRepo.findAllByDepartmentIdIn(departmentIds);
        return disciplines.stream().map(DisciplineWithReqsDTO::new).collect(Collectors.toList());
    }
}
