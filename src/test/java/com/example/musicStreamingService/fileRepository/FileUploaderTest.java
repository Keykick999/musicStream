package com.example.musicStreamingService.fileRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileUploaderTest {

    @Autowired
    private com.example.musicStreamingService.service.FileUploader fileUploader;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private MultipartFile testFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure the upload directory exists
        Files.createDirectories(Paths.get(uploadDir));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test files after each test
        Files.walk(Paths.get(uploadDir))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testUploadLocalFile() throws IOException {
        // 경로에 있는 파일을 읽어와서 MultipartFile로 변환
        File file = new File("C:\\Users\\brian\\Downloads\\릴스 편집본.mp4");
        FileInputStream input = new FileInputStream(file);
        testFile = new MockMultipartFile("file", file.getName(), "video/mp4", input);

        // 파일 업로드
        URI location = fileUploader.uploadFile(testFile);
        assertNotNull(location);

        // 업로드된 파일 확인
        Path path = Paths.get(uploadDir + file.getName());
        assertTrue(Files.exists(path));
    }

    @Test
    public void testGetFile() throws IOException {
        // 경로에 있는 파일을 읽어와서 MultipartFile로 변환
        File file = new File("C:\\Users\\brian\\Downloads\\릴스 편집본.mp4");
        FileInputStream input = new FileInputStream(file);
        testFile = new MockMultipartFile("file", file.getName(), "video/mp4", input);

        // 파일 업로드
        fileUploader.uploadFile(testFile);

        // 파일 조회
        byte[] fileBytes = fileUploader.getFile(file.getName());
        assertNotNull(fileBytes);
        assertTrue(fileBytes.length > 0);
    }

    @Test
    public void testListFiles() throws IOException {
        // 경로에 있는 파일을 읽어와서 MultipartFile로 변환
        File file = new File("C:\\Users\\brian\\Downloads\\릴스 편집본.mp4");
        FileInputStream input = new FileInputStream(file);
        testFile = new MockMultipartFile("file", file.getName(), "video/mp4", input);

        // 파일 업로드
        fileUploader.uploadFile(testFile);

        // 파일 목록 조회
        List<String> files = fileUploader.listFiles();
        assertNotNull(files);
        assertTrue(files.contains(file.getName()));
    }

    @Test
    public void testDeleteFile() throws IOException {
        // 경로에 있는 파일을 읽어와서 MultipartFile로 변환
        File file = new File("C:\\Users\\brian\\Downloads\\릴스 편집본.mp4");
        FileInputStream input = new FileInputStream(file);
        testFile = new MockMultipartFile("file", file.getName(), "video/mp4", input);

        // 파일 업로드
        fileUploader.uploadFile(testFile);

        // 파일 삭제
        fileUploader.deleteFile(file.getName());

        // 삭제된 파일 확인
        Path path = Paths.get(uploadDir + file.getName());
        assertFalse(Files.exists(path));
    }
}
