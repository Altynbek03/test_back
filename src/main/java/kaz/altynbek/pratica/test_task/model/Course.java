package kaz.altynbek.pratica.test_task.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @Override
    public String toString() {
        return "Course{" +
               "id=" +
               id +
               ", name='" +
               name +
               '\'' +
               ", description='" +
               description +
               '\'' +
               ", startDate=" +
               startDate +
               ", endDate=" +
               endDate +
               ", students=" +
               studentsToString() +
               '}';
    }

    private String studentsToString() {
        if (students == null || students.isEmpty()) {
            return "[]";
        }
        return students.stream()
                       .map(student -> "{id=" +
                                       student.getId() +
                                       ", name=" +
                                       student.getFirstName() +
                                       " " +
                                       student.getLastName() +
                                       ", email=" +
                                       student.getEmail() +
                                       "}")
                       .toList()
                       .toString();
    }
}
