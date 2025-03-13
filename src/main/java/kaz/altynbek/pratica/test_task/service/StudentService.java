package kaz.altynbek.pratica.test_task.service;

import jakarta.transaction.Transactional;
import kaz.altynbek.pratica.test_task.dto.request.DeleteStudentRequest;
import kaz.altynbek.pratica.test_task.dto.request.EditStudentRequest;
import kaz.altynbek.pratica.test_task.dto.request.RegisterStudentToCourseRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.exception.StudentAlreadyExistsException;
import kaz.altynbek.pratica.test_task.exception.StudentNotFoundException;
import kaz.altynbek.pratica.test_task.model.Course;
import kaz.altynbek.pratica.test_task.model.Student;
import kaz.altynbek.pratica.test_task.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public BaseResponse updateStudent(EditStudentRequest request) {
        try {
            var student = getStudentByEmail(request.getEmail());
            checkIsExistsByEmail(request.getNewEmail());
            changeStudentFields(student, request);
            return BaseResponse.builder().success(true).errorCode(0).message("Success update student").build();
        } catch (StudentAlreadyExistsException | StudentNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating student", e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    @Transactional
    public BaseResponse deleteStudent(DeleteStudentRequest request) {
        try {
            var student = getStudentById(request.getStudentId());
            studentRepository.delete(student);
            return BaseResponse.builder().success(true).errorCode(0).message("Success delete student").build();
        } catch (StudentNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        }
        catch (Exception e) {
            log.error("Unexpected error occurred while deleting student", e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }
    
    public Boolean isStudentExistsByEmailAndCourse(String email, Course course) {
        return studentRepository.existsByEmailAndCourse(email, course);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                                .orElseThrow(() -> new StudentNotFoundException(String.format("Student with id %s not found", id)));
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                                .orElseThrow(() -> new StudentNotFoundException(String.format("Student with email %s not found", email)));
    }

    private void changeStudentFields(Student student, EditStudentRequest request) {
        if (request.getNewEmail() != null) {
            student.setEmail(request.getNewEmail());
        }

        if (request.getNewFirstName() != null && !student.getFirstName().equals(request.getNewFirstName())) {
            student.setFirstName(request.getNewFirstName());
        }

        if (request.getNewLastName() != null && !student.getLastName().equals(request.getNewLastName())) {
            student.setLastName(request.getNewLastName());
        }

        studentRepository.save(student);
    }

    public Student createAndGetStudent(RegisterStudentToCourseRequest request, Course course) {
        var student = new Student();
        student.setEmail(request.getEmail());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setCourse(course);
        return studentRepository.save(student);
    }

    public void checkIsExistsByEmail(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new StudentAlreadyExistsException("Student with email " + email + " already exists");
        }
    }

}
