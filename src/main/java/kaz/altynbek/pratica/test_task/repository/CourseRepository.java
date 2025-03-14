package kaz.altynbek.pratica.test_task.repository;

import kaz.altynbek.pratica.test_task.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);

    Optional<Course> findById(Long id);

    boolean existsByName(String name);
}
