package com.othmanfrdev.projecttrackerapi.repository;

import com.othmanfrdev.projecttrackerapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.budget IS NULL")
    List<Project> findProjectsByBudgetIsNull();
}
