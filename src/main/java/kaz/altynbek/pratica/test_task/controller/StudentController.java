package kaz.altynbek.pratica.test_task.controller;

import jakarta.validation.Valid;
import kaz.altynbek.pratica.test_task.dto.request.DeleteStudentRequest;
import kaz.altynbek.pratica.test_task.dto.request.EditStudentRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/edit")
    public BaseResponse updateStudent(@RequestBody @Valid EditStudentRequest request) {
        log.info("start updateStudent operation with requestId: {}", request.getRequestId());
        log.debug("start updateStudent operation with request: {}", request);
        var response = studentService.updateStudent(request);
        log.info("end updateStudent operation with requestId: {}", request.getRequestId());
        log.debug("end updateStudent operation with response: {}", response);
        return response;
    }

    @PostMapping("/delete")
    public BaseResponse deleteStudent(@RequestBody @Valid DeleteStudentRequest request) {
        log.info("start deleteStudent operation with requestId: {}", request.getRequestId());
        log.debug("start deleteStudent operation with request: {}", request);
        var response = studentService.deleteStudent(request);
        log.info("end deleteStudent operation with requestId: {}", request.getRequestId());
        log.debug("end deleteStudent operation with response: {}", response);
        return response;
    }
}
