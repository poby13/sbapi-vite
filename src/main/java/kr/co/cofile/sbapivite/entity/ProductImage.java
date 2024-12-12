package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.nio.file.Path;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Setter
    private Long imageId;

    @Setter
    private Long productPno;

    private String fileName;
    private String filePath; // Path
    private String fileType;
    private String thumbnailPath; // Path


    private LocalDateTime uploadDate;

    @Setter
    private int sequence;

}
