package kr.co.cofile.sbapivite.util;

import jakarta.annotation.PostConstruct;
import kr.co.cofile.sbapivite.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${kr.co.cofile.sbapivite.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);

        if(!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();

        log.info("----------------------------------------");
        log.info(uploadPath);
    }

    public List<ProductImage> saveFiles(List<MultipartFile> files) throws RuntimeException {

        if(files == null || files.isEmpty()) {
            return List.of();
        }

        //List<String> uploadNames = new ArrayList<>();
        List<ProductImage> productImages = new ArrayList<>();

        int seq = 0;

        for (MultipartFile file : files) {

            String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                // 원본파일 저장
                saveOriginalFile(file, savePath);

                // 파일타입
                String contentType = detectContentType(file);

                if(contentType != null && contentType.startsWith("image")) {
                    // 썸네일 파일 저장
                    Path thumbnailPath = generateThumbnail(file, savedName, contentType);

                    // 첨부파일 등록
                    ProductImage productImage = ProductImage.builder()
                            .fileName(savedName)
                            .filePath(savePath.toString())
                            .fileType(contentType)
                            .thumbnailPath(thumbnailPath.toString())
                            .sequence(++seq)
                            .build();
                    productImages.add(productImage);
                }

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return productImages;
    }

    private void saveOriginalFile(MultipartFile file, Path savePath) throws IOException {
        Files.copy(file.getInputStream(), savePath);
    }

    private String detectContentType(MultipartFile file)throws IOException {
        Tika tika = new Tika();
        return tika.detect(file.getBytes());
    }

    // 썸네일 파일 저장
    private Path generateThumbnail(MultipartFile file, String savedName, String contentType) throws IOException {
        Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

        // JFIF 형식일 경우 처리
        if (contentType.equals("image/jpeg") && file.getOriginalFilename().toLowerCase().endsWith(".jfif")) {
            // .jfif를 .jpg로 변경
            thumbnailPath = Paths.get(uploadPath, "s_" + savedName.substring(0, savedName.length() - 5) + ".jpg");

            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage convertedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_3BYTE_BGR
            );
            Graphics2D g2d = convertedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            // 썸네일 저장
            Thumbnails.of(convertedImage)
                    .size(200, 200)
                    .keepAspectRatio(true) // 가로/세로 비율 무시
                    .crop(Positions.CENTER) // 중앙 기준으로 크롭
                    .outputQuality(0.5)
                    .outputFormat("jpg")
                    .toFile(thumbnailPath.toFile());
        } else {
            // JFIF 형식이 아닌 경우 처리
            Thumbnails.of(file.getInputStream())
                    .size(200, 200)
                    .keepAspectRatio(true) // 가로/세로 비율 무시
                    .crop(Positions.CENTER) // 중앙 기준으로 크롭
                    .outputQuality(0.5)
                    .outputFormat("jpg")
                    .toFile(thumbnailPath.toFile());
        }
        return thumbnailPath;
    }

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        if (!resource.isReadable()) {
            resource = new FileSystemResource(uploadPath + File.separator + "default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body((Resource) resource);
    }

    public void deleteFiles(List<String> fileNames) {

        if(fileNames == null || fileNames.isEmpty()) {
           return;
        }

        fileNames.forEach(fileName -> {
            String thumbnailFileName = "s_" + fileNames;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch(IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
