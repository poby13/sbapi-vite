package kr.co.cofile.sbapivite.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {
    private Long tno;
    private String title;
    private String writer;
    private Boolean complete;
    private LocalDate dueDate;
}