package kaz.altynbek.pratica.test_task.model;

import jakarta.persistence.*;
import kaz.altynbek.pratica.test_task.model.Role.Role;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
