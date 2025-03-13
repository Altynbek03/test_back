package kaz.altynbek.pratica.test_task.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCourseRequest {
    @NotBlank(message = "поле request_id не может быть пустым")
    private String requestId;
    @NotBlank(message = "поле course_name не может быть пустым")
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
}
