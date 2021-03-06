package com.poo.backend.controllers;

import com.poo.backend.dto.*;
import com.poo.backend.search.SearchByExactMatch;
import com.poo.backend.search.SearchByFuzzy;
import com.poo.backend.search.SearchByRegex;
import com.poo.backend.services.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public List<DisciplineWithoutReqsDTO> findAll() {
        return disciplineService.findAll();
    }

    @GetMapping(path = "/recommendations")
    public List<DisciplineWithoutReqsDTO> getRecommendations(@RequestParam("departmentsId") List<Long> ids,
                                                             @RequestParam("keywords") List<String> keywords,
                                                             @RequestParam("disciplinesCode") List<String> disciplinesCode) {
        List<DisciplineWithoutReqsDTO> disciplines;
        disciplines = disciplineService.findAllByDepartmentsId(ids);
        disciplines = (List<DisciplineWithoutReqsDTO>) findAllByKeywordsMatch(disciplines, keywords);

        return (List<DisciplineWithoutReqsDTO>) removeDups(disciplines, disciplinesCode);
    }


    @GetMapping(path = "/possible-recommendations")
    public List<DisciplineWithReqsDTO> getPossibleRecommendations(@RequestParam("departmentsId") List<Long> ids,
                                                                  @RequestParam("keywords") List<String> keywords,
                                                                  @RequestParam("disciplinesCode") List<String> disciplinesCode,
                                                                  @RequestParam("courseCode") String courseCode) {
        List<DisciplineWithReqsDTO> disciplines;
        disciplines = disciplineService.findAllWithRequisitesByDepartmentsId(ids);
        disciplines = (List<DisciplineWithReqsDTO>) findAllByKeywordsMatch(disciplines, keywords);
        disciplines = (List<DisciplineWithReqsDTO>) removeDups(disciplines, disciplinesCode);
        filterPreReqsByCourseCode(disciplines, courseCode);
        disciplines = disciplinesPossibleToDo(disciplines, disciplinesCode);

        return disciplines;
    }

    private List<? extends DisciplineDTO> findAllByKeywordsMatch(List<? extends DisciplineDTO> disciplines, List<String> keywords) {
        List<String> disciplinesNames;
        List<Integer> disciplinesIndexes;

        if (keywords.size() == 0) return disciplines;

        disciplinesNames = disciplines.stream().map(DisciplineDTO::getName).collect(Collectors.toList());
        disciplinesIndexes = disciplinesThatMatchWithKeywords(keywords, disciplinesNames);

        return disciplinesIndexes.stream().map(disciplines::get).collect(Collectors.toList());
    }

    private List<Integer> disciplinesThatMatchWithKeywords(List<String> keywords,
                                                           List<String> disciplinesNames) {
        List<Integer> resultsIndex;
        List<String> regexKeywords, fuzzyKeywords, exactMatchKeywords;

        regexKeywords = keywords.stream().filter(SearchByRegex::isARegexKeyword).collect(Collectors.toList());
        keywords = keywords.stream().filter(keyword -> !SearchByRegex.isARegexKeyword(keyword)).collect(Collectors.toList());

        fuzzyKeywords = keywords.stream().filter(keyword -> keyword.split(" ").length < 3).collect(Collectors.toList());
        exactMatchKeywords = keywords.stream().filter(keyword -> keyword.split(" ").length >= 3).collect(Collectors.toList());

        resultsIndex = new SearchByRegex().doSearch(regexKeywords, disciplinesNames);
        resultsIndex.addAll(new SearchByFuzzy().doSearch(fuzzyKeywords, disciplinesNames));
        resultsIndex.addAll(new SearchByExactMatch().doSearch(exactMatchKeywords, disciplinesNames));

        return resultsIndex;
    }

    private List<? extends DisciplineDTO> removeDups(List<? extends DisciplineDTO> disciplines, List<String> disciplinesAlredyDone) {
        Set<String> disciplinesCode = new HashSet<>(disciplinesAlredyDone);
        return disciplines.stream().filter(discipline -> {
            String code = discipline.getCode();
            return !disciplinesCode.contains(code) && (disciplinesCode.add(code));
        }).collect(Collectors.toList());
    }

    private void filterPreReqsByCourseCode(List<DisciplineWithReqsDTO> disciplines, String courseCode) {
        disciplines.forEach(discipline -> {
            List<RequisitesByCourseDTO> requisites, filteredRequisites;
            requisites = discipline.getRequisites();
            if (requisites.size() > 0) {
                filteredRequisites = requisites.stream()
                        .filter(requisite -> Objects.equals(requisite.getCourseCode(), courseCode))
                        .collect(Collectors.toList());

                if (filteredRequisites.size() == 0)
                    filteredRequisites = List.of(requisites.get(0));

                discipline.setRequisites(filteredRequisites);
            }
        });
    }

    private List<DisciplineWithReqsDTO> disciplinesPossibleToDo(List<DisciplineWithReqsDTO> disciplines, List<String> preReqsCode) {
        return disciplines.stream()
                .filter(discipline -> {
                    List<RequisitesByCourseDTO> requisitesByCourse = discipline.getRequisites();
                    List<RequisiteDTO> requisites = new ArrayList<>();
                    boolean hasAllPreReqs;

                    if (requisitesByCourse.size() > 0)
                        requisites = requisitesByCourse.get(0).getRequisites();

                    hasAllPreReqs = requisites.stream().allMatch(requisite -> {
                        String code = requisite.getDiscipline().split("-")[0];
                        return preReqsCode.contains(code.trim());
                    });

                    return hasAllPreReqs;
                })
                .collect(Collectors.toList());
    }
}
