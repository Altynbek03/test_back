package kaz.altynbek.pratica.test_task.service;

import jakarta.transaction.Transactional;
import kaz.altynbek.pratica.test_task.dto.request.AuthRequest;
import kaz.altynbek.pratica.test_task.dto.request.RegisterUserRequest;
import kaz.altynbek.pratica.test_task.dto.response.BaseResponse;
import kaz.altynbek.pratica.test_task.exception.UserAlreadyExistsException;
import kaz.altynbek.pratica.test_task.model.Role.Role;
import kaz.altynbek.pratica.test_task.model.User;
import kaz.altynbek.pratica.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public BaseResponse authenticate(AuthRequest request) {
        try {
            var user = findUserByEmail(request.getUsername());
            validatePassword(user.getPassword(), request.getPassword());
            var token = jwtService.generateToken(user);
            return BaseResponse.builder().success(true).errorCode(0).message(token).build();
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error while authenticating user", e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    @Transactional
    public BaseResponse registerUser(RegisterUserRequest registerUserRequest, Role role) {
        try {
            isUserExists(registerUserRequest.getEmail());
            var user = registerAndGetUser(registerUserRequest, role);
            var jwtToken = jwtService.generateToken(user);
            return BaseResponse.builder().success(true).errorCode(0).message(jwtToken).build();
        } catch (UserAlreadyExistsException e) {
            log.error(e.getMessage());
            return BaseResponse.builder().success(false).errorCode(400).message(e.getMessage()).build();
        } catch (Exception e) {
            log.error("Unexpected error while registering user, errorMessage : {}", e.getMessage(), e);
            return BaseResponse.builder().success(false).errorCode(500).message(e.getMessage()).build();
        }
    }

    private User registerAndGetUser(RegisterUserRequest registerUserRequest, Role role) {
        var user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setFirstName(registerUserRequest.getFirstName());
        user.setSecondName(registerUserRequest.getLastName());
        user.setRole(role);
        return userRepository.save(user);
    }

    private void isUserExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", email));
        }
    }

    private void validatePassword(String userPassword, String requestPassword) {
        if (!passwordEncoder.matches(requestPassword, userPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }

}
