package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.ProjectRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.ProjectResponse;
import com.othmanfrdev.projecttrackerapi.entity.Project;
import com.othmanfrdev.projecttrackerapi.entity.User;
import com.othmanfrdev.projecttrackerapi.exception.ProjectValidationException;
import com.othmanfrdev.projecttrackerapi.repository.ProjectRepository;
import com.othmanfrdev.projecttrackerapi.repository.UserRepository;
import com.othmanfrdev.projecttrackerapi.service.ProjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectServiceImplTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @AfterEach
    void tearDown() {
        this.projectRepository.deleteAll();
    }

    @Test
    void findAllProject() {
        generateProject();
        List<ProjectResponse> allProjects = this.projectService.findAllProject();
        Assertions.assertEquals(1,allProjects.size());
        Assertions.assertEquals("project",allProjects.get(0).getName());
        Assertions.assertEquals("name",allProjects.get(0).getCreatedBy());
    }

    @Test
    void findProjectById() {
        generateProject();
        ProjectResponse project = this.projectService.findProjectById(3L);
        Assertions.assertEquals("project",project.getName());
        Assertions.assertEquals("name",project.getCreatedBy());
    }

    @Test
    void saveProjectWhenProjectIsValid() {
        generateUser();
        ProjectRequest request=ProjectRequest
                .builder()
                .name("project")
                .userId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .build();
        ProjectResponse response = this.projectService.saveProject(request);
        Assertions.assertEquals("project",response.getName());
        Assertions.assertEquals("name",response.getCreatedBy());
    }
    @Test
    void saveProjectWhenProjectIsNotValid() {
        ProjectRequest request=ProjectRequest
                .builder()
                .name("project")
                .categoryId(1L)
                .userId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().minusDays(12))
                .build();
        Assertions.assertThrows(ProjectValidationException.class,
                ()->this.projectService.saveProject(request),
                "StartDate must be before EndDate.");
    }

    @Test
    void deleteProject() {
        this.projectService.deleteProject(1L);
        Assertions.assertEquals(0,this.projectRepository.findAll().size());
    }
    Project generateProject() {
        return this.projectRepository.save(Project
                .builder()
                .user(generateUser())
                .name("project")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .build());
    }
    User generateUser(){
        return this.userRepository.save(User.builder().name("name").password("123456").email("test@gmail.Com").build());
    }

}