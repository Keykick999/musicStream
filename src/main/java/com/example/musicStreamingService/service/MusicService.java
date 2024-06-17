package com.example.musicStreamingService.service;

import com.example.musicStreamingService.dto.UploadRequest;
import com.example.musicStreamingService.entity.Member;
import com.example.musicStreamingService.entity.Music;
import com.example.musicStreamingService.fileRepository.FileUploader;
import com.example.musicStreamingService.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private FileUploader fileUploader;

    /**저장**/
    public void save(UploadRequest uploadRequest, Member artist) throws IOException {
        MultipartFile audioFile = uploadRequest.getAudioFile();
        if(audioFile == null ) {
            return;
        }
        //오디오 파일 저장
        String path = fileUploader.uploadFile(audioFile);

        //이렇게 예외 처리 하면 안 될듯??....
        if(path == null) {
            throw new IllegalStateException("파일 저장 실패");
        }

        String title = uploadRequest.getMusicTitle();
        String genre = uploadRequest.getMusicGenre();
        String lyrics = uploadRequest.getMusicLyrics();
        //artist값은 세션에서 가져오기
        Music newMusic = new Music(title, genre, lyrics, path, artist);
        musicRepository.save(newMusic);

    }
}









