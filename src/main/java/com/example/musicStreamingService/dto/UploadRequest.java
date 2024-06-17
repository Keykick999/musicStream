package com.example.musicStreamingService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadRequest {
    private String musicTitle;
    private String musicGenre;
    private String musicLyrics;
    private MultipartFile audioFile;
}
