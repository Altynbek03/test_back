package kaz.altynbek.pratica.test_task.service;

import jakarta.transaction.Transactional;
import kaz.altynbek.pratica.test_task.dto.request.CreateCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.DeleteCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.EditCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.RegisterStudentToCourseRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.exception.CourseAlreadyExistsException;
import kaz.altynbek.pratica.test_task.exception.CourseNotFoundException;
import kaz.altynbek.pratica.test_task.exception.StudentAlreadyExistsException;
import kaz.altynbek.pratica.test_task.exception.UserAlreadyExistsException;
import kaz.altynbek.pratica.test_task.model.Course;
import kaz.altynbek.pratica.test_task.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final EmailService emailService;

    @Transactional
    public BaseResponse createCourse(CreateCourseRequest createCourseRequest) {
        try {
            checkIsExists(createCourseRequest.getCourseName());
            createAndSaveCourse(createCourseRequest);
            return BaseResponse.builder().success(true).errorCode(0).message("success create course").build();
        } catch (CourseAlreadyExistsException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error while creating course, errorMessage: {}", e.getMessage(), e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    @Transactional
    public BaseResponse editCourse(EditCourseRequest editCourseRequest) {
        try {
            changeCourseFields(editCourseRequest);
            return BaseResponse.builder().success(true).errorCode(0).message("success course edit").build();
        } catch (CourseNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error while editing course, errorMessage: {}", e.getMessage(), e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    @Transactional
    public BaseResponse deleteCourse(DeleteCourseRequest deleteCourseRequest) {
        try {
            var course = getCourseById(deleteCourseRequest.getCourseId());
            courseRepository.delete(course);
            return BaseResponse.builder().success(true).errorCode(0).message("success delete course").build();
        } catch (CourseNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error while deleting course, errorMessage: {}", e.getMessage(), e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    @Transactional
    public BaseResponse registerStudentToCourse(RegisterStudentToCourseRequest request) {
        try {
            studentService.checkIsExistsByEmail(request.getEmail());
            var course = getCourseById(request.getCourseId());
            checkStudentExistsByCourse(course, request.getEmail());
            var student = studentService.createAndGetStudent(request, course);
            CompletableFuture.runAsync(() -> emailService.sendRegistrationEmail(student, course));
            return BaseResponse.builder().success(true).errorCode(0).message("success course register").build();
        } catch (CourseNotFoundException | UserAlreadyExistsException | StudentAlreadyExistsException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    public BaseResponse getInfo(Long courseId) {
        try {
            var course = getCourseById(courseId);
            return BaseResponse.builder().success(true).errorCode(0).message(course.toString()).build();
        } catch (CourseNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    private void checkStudentExistsByCourse(Course course, String email) {
        if (Boolean.TRUE.equals(studentService.isStudentExistsByEmailAndCourse(email, course))) {
            throw new StudentAlreadyExistsException("Student already exists in course with email: " + email);
        }
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                               .orElseThrow(() -> new CourseNotFoundException(String.format("course with id %s not found", courseId)));
    }

    private void changeCourseFields(EditCourseRequest editCourseRequest) {
        var course = getCourseByName(editCourseRequest.getOldCourseName());
        if (editCourseRequest.getNewCourseName() != null && !course.getName().equals(editCourseRequest.getNewCourseName())) {
            course.setName(editCourseRequest.getNewCourseName());
        }

        if (editCourseRequest.getNewDescription() != null && !course.getDescription().equals(editCourseRequest.getNewDescription())) {
            course.setDescription(editCourseRequest.getNewDescription());
        }

        if (editCourseRequest.getNewStartDate() != null && !course.getStartDate().equals(editCourseRequest.getNewStartDate())) {
            course.setStartDate(editCourseRequest.getNewStartDate());
        }

        if (editCourseRequest.getNewEndDate() != null && !course.getEndDate().equals(editCourseRequest.getNewEndDate())) {
            course.setEndDate(editCourseRequest.getNewEndDate());
        }

        courseRepository.save(course);
    }

    public Course getCourseByName(String courseName) {
        return courseRepository.findByName(courseName)
                               .orElseThrow(() -> new CourseNotFoundException(String.format("Course with name %s not found", courseName)));
    }

    private void checkIsExists(String courseName) {
        if (courseRepository.existsByName(courseName)) {
            throw new CourseAlreadyExistsException(String.format("%s already exists", courseName));
        }
    }

    private void createAndSaveCourse(CreateCourseRequest createCourseRequest) {
        var course = new Course();
        course.setName(createCourseRequest.getCourseName());
        course.setDescription(createCourseRequest.getCourseName());
        course.setStartDate(createCourseRequest.getStartDate());
        course.setEndDate(createCourseRequest.getEndDate());
        courseRepository.save(course);
    }

}
