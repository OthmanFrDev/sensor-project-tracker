package com.othmanfrdev.projecttrackerapi.repository;

import com.othmanfrdev.projecttrackerapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
