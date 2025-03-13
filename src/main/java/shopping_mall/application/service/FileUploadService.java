package shopping_mall.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadService {


    private final String IMAGE_UPLOAD_DIR;

    public FileUploadService(@Value("${file.image.upload-dir}")String IMAGE_UPLOAD_DIR) {
        this.IMAGE_UPLOAD_DIR = IMAGE_UPLOAD_DIR;
    }

    @Transactional
    public String uploadImage(MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            throw new IOException("파일이 비어있습니다.");
        }
        final String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        // 업로드 경로 절대 경로로 설정
        Path uploadPath = Paths.get(System.getProperty("user.dir"), IMAGE_UPLOAD_DIR, fileName);

        // 디렉토리가 존재하지 않으면 생성
        File directory = uploadPath.getParent().toFile();
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        // 파일 저장
        File destFile = uploadPath.toFile();
        imageFile.transferTo(destFile); // 실제 파일 저장

        return fileName;
    }
}
