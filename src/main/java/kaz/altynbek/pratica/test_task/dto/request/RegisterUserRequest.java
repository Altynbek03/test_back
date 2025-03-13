package kaz.altynbek.pratica.test_task.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterUserRequest {
    @NotBlank(message = "поле request_id не может быть пустым")
    private String requestId;
    @Email(message = "неверный формат почты")
    private String email;
    @NotBlank(message = "поле firs_name не может быть пустым")
    private String firstName;
    @NotBlank(message = "поле last_name не может быть пустым")
    private String lastName;
    @NotBlank(message = "поле password не может быть пустым")
    private String password;
}
