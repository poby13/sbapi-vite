package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private Long imageIo;
    private Long productPno;
    private String fileName;
    private String filePath;
    private String fileType;
    private LocalDateTime uploadDate;
    @Setter
    private int sequence;

}
