package kaz.altynbek.pratica.test_task.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "student", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "course_id"}))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
