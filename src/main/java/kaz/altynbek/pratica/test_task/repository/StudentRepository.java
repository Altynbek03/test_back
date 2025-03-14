package kaz.altynbek.pratica.test_task.repository;

import kaz.altynbek.pratica.test_task.model.Course;
import kaz.altynbek.pratica.test_task.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);

    boolean existsByEmailAndCourse(String email, Course course);
}
