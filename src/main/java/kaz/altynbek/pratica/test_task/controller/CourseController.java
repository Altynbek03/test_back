package kaz.altynbek.pratica.test_task.controller;

import jakarta.validation.Valid;
import kaz.altynbek.pratica.test_task.dto.request.CreateCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.DeleteCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.EditCourseRequest;
import kaz.altynbek.pratica.test_task.dto.request.RegisterStudentToCourseRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public BaseResponse createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {
        log.info("start createCourse operation with requestId: {}", createCourseRequest.getRequestId());
        log.debug("start createCourse operation with request: {}", createCourseRequest);
        var response = courseService.createCourse(createCourseRequest);
        log.info("end createCourse operation with requestId: {}", createCourseRequest.getRequestId());
        log.debug("end createCourse operation with response: {}", response);
        return response;
    }

    @PostMapping("/update")
    public BaseResponse editCourse(@RequestBody @Valid EditCourseRequest editCourseRequest) {
        log.info("start editCourse operation with requestId: {}", editCourseRequest.getRequestId());
        log.debug("start editCourse operation with request: {}", editCourseRequest);
        var response = courseService.editCourse(editCourseRequest);
        log.info("end editCourse operation with requestId: {}", editCourseRequest.getRequestId());
        log.debug("end editCourse operation with response: {}", response);
        return response;
    }

    @PostMapping("/delete")
    public BaseResponse deleteCourse(@RequestBody @Valid DeleteCourseRequest deleteCourseRequest) {
        log.info("start deleteCourse operation with requestId: {}", deleteCourseRequest.getRequestId());
        log.debug("start deleteCourse operation with request: {}", deleteCourseRequest);
        var response = courseService.deleteCourse(deleteCourseRequest);
        log.info("end deleteCourse operation with requestId: {}", deleteCourseRequest.getRequestId());
        log.debug("end deleteCourse operation with response: {}", response);
        return response;
    }

    @PostMapping("/register-student")
    public BaseResponse registerStudent(@RequestBody @Valid RegisterStudentToCourseRequest request) {
        log.info("start registerStudent operation with requestId: {}", request.getRequestId());
        log.debug("start registerStudent operation with request: {}", request);
        var response = courseService.registerStudentToCourse(request);
        log.info("end registerStudent operation with requestId: {}", request.getRequestId());
        log.debug("end registerStudent operation with response: {}", response);
        return response;
    }

    @GetMapping("/get-info/{courseId}")
    public BaseResponse getCourseInfo(@PathVariable("courseId") Long courseId) {
        log.info("start getCourseInfo operation with requestId: {}", courseId);
        log.debug("start getCourseInfo operation with request: {}", courseId);
        var response = courseService.getInfo(courseId);
        log.info("end getCourseInfo operation with requestId: {}", courseId);
        log.debug("end getCourseInfo operation with response: {}", response);
        return response;
    }

}
