package com.othmanfrdev.projecttrackerapi.service;

import com.othmanfrdev.projecttrackerapi.dto.request.ProjectRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> findAllProject();
    List<ProjectResponse> findAllProjectByBudgetIsNull();
    ProjectResponse findProjectById(Long id);
    ProjectResponse saveProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id,ProjectRequest projectRequest);
    void deleteProject(Long id);
}
