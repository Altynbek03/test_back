package kaz.altynbek.pratica.test_task.controller;

import jakarta.validation.Valid;
import kaz.altynbek.pratica.test_task.dto.request.AuthRequest;
import kaz.altynbek.pratica.test_task.dto.request.RegisterUserRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.model.Role.Role;
import kaz.altynbek.pratica.test_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-admin")
    public BaseResponse registerAdmin(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        log.info("starting register-admin operation with requestId: {}", registerUserRequest.getRequestId());
        log.debug("registerUserRequest: {}", registerUserRequest);
        var response = authService.registerUser(registerUserRequest, Role.ADMIN);
        log.info("Sending register-admin operation response with requestId: {}", registerUserRequest.getRequestId());
        log.debug("Sending register-admin response: {}", response);
        return response;
    }

    @PostMapping("/register-user")
    public BaseResponse registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        log.info("starting register-user operation with requestId: {}", registerUserRequest.getRequestId());
        log.debug("registerUserRequest: {}", registerUserRequest);
        var response = authService.registerUser(registerUserRequest, Role.USER);
        log.info("Sending register-student operation response with requestId: {}", registerUserRequest.getRequestId());
        log.debug("Sending register-student response: {}", response);
        return response;
    }

    @PostMapping("/get-token")
    public BaseResponse getToken(@Valid @RequestBody AuthRequest request) {
        log.info("starting get-token operation with requestId: {}", request.getRequestId());
        log.debug("request: {}", request);
        var response = authService.authenticate(request);
        log.info("Sending authenticate operation response with requestId: {}", request.getRequestId());
        log.debug("Sending authenticate response: {}", response);
        return response;
    }

}
