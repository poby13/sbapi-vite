package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    private Long tno;       // Primary Key
    private String title;   // 할 일 제목
    private String writer;  // 작성자
    private Boolean complete; // 완료 여부
    private LocalDate dueDate; // 마감일
}