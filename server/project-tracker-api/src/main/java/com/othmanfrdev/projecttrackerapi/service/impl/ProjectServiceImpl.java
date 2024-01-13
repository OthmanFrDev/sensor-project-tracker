package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.ProjectRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.ProjectResponse;
import com.othmanfrdev.projecttrackerapi.entity.Budget;
import com.othmanfrdev.projecttrackerapi.entity.Category;
import com.othmanfrdev.projecttrackerapi.entity.Project;
import com.othmanfrdev.projecttrackerapi.entity.User;
import com.othmanfrdev.projecttrackerapi.exception.EntityNotFoundException;
import com.othmanfrdev.projecttrackerapi.exception.ProjectValidationException;
import com.othmanfrdev.projecttrackerapi.repository.BudgetRepository;
import com.othmanfrdev.projecttrackerapi.repository.CategoryRepository;
import com.othmanfrdev.projecttrackerapi.repository.ProjectRepository;
import com.othmanfrdev.projecttrackerapi.repository.UserRepository;
import com.othmanfrdev.projecttrackerapi.service.ProjectService;
import com.othmanfrdev.projecttrackerapi.utils.ValidatorUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CategoryRepository categoryRepository,
                              BudgetRepository budgetRepository,
                              UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectResponse> findAllProject() {
        return this.projectRepository
                .findAll()
                .stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponse> findAllProjectByBudgetIsNull() {
        return this.projectRepository
                .findProjectsByBudgetIsNull()
                .stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse findProjectById(Long id) {
        return ProjectResponse.from(getProjectById(id));
    }

    @Override
    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        if (ValidatorUtils.isProjectValid(projectRequest)) {
            throw new ProjectValidationException("StartDate must be before EndDate.");
        }
        if(Objects.isNull(projectRequest.getUserId())){
            throw new ProjectValidationException("Project should be created by connected user");
        }
        User user =userRepository
                .findById(projectRequest.getUserId())
                .orElseThrow(()->new EntityNotFoundException(String.format("User with id %d not found!",projectRequest.getUserId())));
        Project prj = Project.builder()
                .name(projectRequest.getName())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .user(user)
                .build();
        if (Objects.nonNull(projectRequest.getCategoryId())) {
            Category category = this.categoryRepository.findById(projectRequest.getCategoryId()).orElse(null);
            prj.setCategory(category);
        }
        if (Objects.nonNull(projectRequest.getBudgetId())) {
            Budget budget = this.budgetRepository.findById(projectRequest.getBudgetId()).orElse(null);
            prj.setBudget(budget);
        }
        return ProjectResponse.from(this.projectRepository.save(prj));
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        if (ValidatorUtils.isProjectValid(projectRequest)) {
            throw new ProjectValidationException("StartDate must be before EndDate.");
        }
        Project exsitingProject = getProjectById(id);
        if (!isProjectEquals(exsitingProject, projectRequest)) {
            exsitingProject.setName(projectRequest.getName());
            exsitingProject.setStartDate(projectRequest.getStartDate());
            exsitingProject.setEndDate(projectRequest.getEndDate());
            if (Objects.nonNull(projectRequest.getCategoryId())) {
                Category newCategory = this.categoryRepository.findById(projectRequest.getCategoryId()).orElse(null);
                exsitingProject.setCategory(newCategory);
            }
            if (Objects.nonNull(projectRequest.getBudgetId())) {
                Budget newBudget = this.budgetRepository.findById(projectRequest.getBudgetId()).orElse(null);
                exsitingProject.setBudget(newBudget);
            }
            this.projectRepository.save(exsitingProject);
        }
        return ProjectResponse.from(exsitingProject);
    }

    @Override
    public void deleteProject(Long id) {
        this.projectRepository.deleteById(id);
    }

    private Project getProjectById(Long id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Project with id %d not found.", id)));
    }

    private boolean isProjectEquals(Project exsitingProject, ProjectRequest updatedProject) {
        return Objects.nonNull(exsitingProject.getCategory()) &&
                Objects.equals(exsitingProject.getCategory().getId(), updatedProject.getCategoryId()) &&
                Objects.nonNull(exsitingProject.getBudget()) &&
                Objects.equals(exsitingProject.getBudget().getId(), updatedProject.getBudgetId()) &&
                Objects.equals(exsitingProject.getStartDate(), updatedProject.getStartDate()) &&
                Objects.equals(exsitingProject.getEndDate(), updatedProject.getEndDate());
    }
}
