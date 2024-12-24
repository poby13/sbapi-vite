package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Setter
    private Long id;

    @Setter
    private Long productId;

    private String fileName;
    private String filePath; // Path
    private String fileType;
    private String thumbnailPath; // Path


    private LocalDateTime uploadDate;

    @Setter
    private int sequence;

}
