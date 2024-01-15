package com.othmanfrdev.projecttrackerapi;

import com.othmanfrdev.projecttrackerapi.entity.Budget;
import com.othmanfrdev.projecttrackerapi.entity.Category;
import com.othmanfrdev.projecttrackerapi.entity.User;
import com.othmanfrdev.projecttrackerapi.repository.BudgetRepository;
import com.othmanfrdev.projecttrackerapi.repository.CategoryRepository;
import com.othmanfrdev.projecttrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectTrackerApiApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final PasswordEncoder passwordEncoder;


    public ProjectTrackerApiApplication(UserRepository userRepository, CategoryRepository categoryRepository, BudgetRepository budgetRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public static void main(String[] args) {
        SpringApplication.run(ProjectTrackerApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryRepository.saveAll(
                List.of(
                        Category.builder().label("Industrie").build(),
                        Category.builder().label("Assurance").build(),
                        Category.builder().label("Bancaire").build(),
                        Category.builder().label("Finance").build(),
                        Category.builder().label("Agriculture").build(),
                        Category.builder().label("public").build()
                )
        );
        this.budgetRepository.saveAll(
                Stream.of(1, 2, 3, 4)
                        .map(i -> Budget
                                .builder()
                                .manDays((int) (Math.random() * 400))
                                .amount((int) (Math.random() * 80000))
                                .build()
                        ).collect(Collectors.toList())
        );
        this.userRepository.save(User
                .builder()
                .email("email@email.com")
                .name("name")
                .password(passwordEncoder.encode("emsi123"))
                .build());
    }
}
