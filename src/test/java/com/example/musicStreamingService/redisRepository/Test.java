package com.example.musicStreamingService.redisRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Test {

    private static String uploadDir = "uploads/";

    public static String uploadImageFile(File file) throws IOException {
        if(file == null) {
            throw new IllegalStateException("file is empty");
        }

        FileInputStream input = new FileInputStream(file);
        MultipartFile multiFile = new MockMultipartFile("file", file.getName(), "image/jpeg", input);

        byte[] bytes = multiFile.getBytes();
        Path path = Paths.get(uploadDir + multiFile.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);

        return path.toString();
    }


    public static String uploadAudioFile(File file) throws IOException {
        if(file == null) {
            throw new IllegalStateException("file is empty");
        }

        FileInputStream input = new FileInputStream(file);
        MultipartFile multiFile = new MockMultipartFile("file", file.getName(), "audio/mp3", input);

        byte[] bytes = multiFile.getBytes();
        Path path = Paths.get(uploadDir + multiFile.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);

        return path.toString();
    }


    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/brian/Downloads/y2mate.com - FREE JCole Type Beat FOREVER_320kbps.mp3");
        String location = uploadAudioFile(file);
    }
}
