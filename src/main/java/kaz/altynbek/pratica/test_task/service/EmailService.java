package kaz.altynbek.pratica.test_task.service;

import kaz.altynbek.pratica.test_task.model.Course;
import kaz.altynbek.pratica.test_task.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendRegistrationEmail(Student student, Course course) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(student.getEmail());
        message.setSubject("Регистрация на курс");
        message.setText("Вы успешно зарегистрированы на курс: " + course.getName() +
                        ". Дата начала: " + course.getStartDate());
        mailSender.send(message);
    }

}
