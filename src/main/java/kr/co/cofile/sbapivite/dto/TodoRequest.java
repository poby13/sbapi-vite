package kr.co.cofile.sbapivite.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {
    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Writer is required")
    private String writer;

    @NotNull(message = "Complete status is required")
    private Boolean complete;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}
