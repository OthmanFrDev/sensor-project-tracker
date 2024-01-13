package com.othmanfrdev.projecttrackerapi.controller;

import com.othmanfrdev.projecttrackerapi.dto.request.ProjectRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.ProjectResponse;
import com.othmanfrdev.projecttrackerapi.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProject(){
        return new ResponseEntity<>(this.projectService.findAllProject(), HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(this.projectService.findProjectById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> saveProject(@RequestBody ProjectRequest projectRequest){
        return new ResponseEntity<>(this.projectService.saveProject(projectRequest), HttpStatus.CREATED);
    }

    @PutMapping (path = "/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable(name="id") Long id,
                                                         @RequestBody ProjectRequest projectRequest){
        return new ResponseEntity<>(this.projectService.updateProject(id,projectRequest), HttpStatus.OK);
    }

    @DeleteMapping  (path = "/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable(name="id") Long id){
        this.projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/no-assigned")
    public ResponseEntity<List<ProjectResponse>> getNoAssignedProjects(){
        return new ResponseEntity<>(this.projectService.findAllProjectByBudgetIsNull(),HttpStatus.OK);
    }

}
