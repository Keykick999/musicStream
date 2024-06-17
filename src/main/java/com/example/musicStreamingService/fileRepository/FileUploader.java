package com.example.musicStreamingService.fileRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileUploader {

    @Value("${file.upload-dir}")
    private String uploadDir;


    // 파일 업로드
    public String uploadFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new IllegalStateException("file is empty");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);

        return path.toString();
    }




    // 파일 조회
    public byte[] getFile(String filename) throws IOException {
        Path path = Paths.get(uploadDir + filename);
        return Files.readAllBytes(path);
    }

    // 저장된 파일 목록 조회
    public List<String> listFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(uploadDir))) {
            return paths.filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    // 파일 삭제
    public void deleteFile(String filename) throws IOException {
        Path path = Paths.get(uploadDir + filename);
        FileSystemUtils.deleteRecursively(path);
    }
}
