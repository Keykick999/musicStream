package com.example.musicStreamingService.restcontroller;

import com.example.musicStreamingService.dto.MusicDTO;
import com.example.musicStreamingService.dto.UploadRequest;
import com.example.musicStreamingService.entity.Member;
import com.example.musicStreamingService.entity.Music;
import com.example.musicStreamingService.repository.MemberRepository;
import com.example.musicStreamingService.repository.MusicRepository;
import com.example.musicStreamingService.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/music")
@CrossOrigin(origins = "http://127.0.0.1:5501") // 특정 출처에 대해 CORS 허용
public class MusicController {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicService musicService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(value = "/audio", consumes = "multipart/form-data")
    public ResponseEntity<String> saveAudioFile(@RequestPart("musicTitle") String musicTitle,
                                                @RequestPart("musicGenre") String musicGenre,
                                                @RequestPart("musicLyrics") String musicLyrics,
                                                @RequestPart("audioFile") MultipartFile audioFile) {
        try {
            Optional<Member> artist = memberRepository.findById("user001");
            if (artist.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            UploadRequest uploadRequest = new UploadRequest(musicTitle, musicGenre, musicLyrics, audioFile);
            musicService.save(uploadRequest, artist.get());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("successfully saved");
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Music music) {
        if (music == null) {
            return ResponseEntity.badRequest().body("failed to save");
        }
        musicRepository.save(music);
        return ResponseEntity.ok("successfully saved");
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<Music> findById(@PathVariable("musicId") Long musicId) {
        if (musicId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Music> findMusic = musicRepository.findById(musicId);
        if (findMusic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findMusic.get());
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<UrlResource> serveFile(@PathVariable String filename) {
        try {
            System.out.println("실행");
            String decodedFilename = URLDecoder.decode(filename, "UTF-8");
            Path file = Paths.get("C:/방학 과제/musicStreamingService/").resolve(decodedFilename);
            System.out.println("File path: " + file.toString());
            UrlResource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MusicDTO>> getAllTracks(@PathVariable("memberId") String memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Music> musics = musicRepository.findByMemberId(memberId);
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : musics) {
            MusicDTO dto = new MusicDTO(
                    music.getMusicTitle(),
                    encodeURIComponent(music.getMusicPath())  // URL 경로로 변경
            );
            musicDTOS.add(dto);
        }
        return ResponseEntity.ok(musicDTOS);
    }

    private String encodeURIComponent(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (IOException e) {
            return value;
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Music music) {
        if (music == null) {
            return ResponseEntity.badRequest().body("failed to save");
        }
        musicRepository.save(music);
        return ResponseEntity.ok("successfully saved");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody Music music) {
        if (music == null) {
            return ResponseEntity.badRequest().body("failed to save");
        }
        musicRepository.delete(music);
        return ResponseEntity.ok("successfully saved");
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<String> deleteById(@PathVariable("musicId") Long musicId) {
        if (musicId == null) {
            return ResponseEntity.badRequest().body("failed to save");
        }
        musicRepository.deleteById(musicId);
        return ResponseEntity.ok("successfully saved");
    }
}
