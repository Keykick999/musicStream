package com.example.musicStreamingService.redisRepository;

import com.example.musicStreamingService.entity.Member;
import com.example.musicStreamingService.entity.Music;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisMusicRepositoryTest {

    @Autowired
    private RedisMusicRepository redisMusicRepository;

    @Autowired
    private RedisTemplate<String, Music> redisTemplate;

    private Music music;
    private Member artist;

    @BeforeEach
    public void setUp() {
        artist = new Member();
        artist.setMemberId("artist123");

        music = new Music();
        music.setMusicId(1L);
        music.setMusicTitle("Test Song");
        music.setMusicGenre("Pop");
        music.setMusicLikes(0);
        music.setMusicDislikes(0);
        music.setMusicLyrics("These are the lyrics.");
        music.setMusicViews(0);
        music.setArtist(artist);
    }

    @Test
    public void testSaveAndFindById() {
        // 저장
        redisMusicRepository.saveIdAsKey(music);

        // 조회
        Optional<Music> retrievedMusic = redisMusicRepository.findById(1L);
        assertTrue(retrievedMusic.isPresent());
        assertEquals("Test Song", retrievedMusic.get().getMusicTitle());
    }

    @Test
    public void testSaveAndFindByTitle() {
        // 저장
        redisMusicRepository.saveTitleAsKey(music);

        // 조회
        Optional<Music> retrievedMusic = redisMusicRepository.findByTitle("Test Song");
        System.out.println("retrievedMusic.get() = " + retrievedMusic.get());
        assertTrue(retrievedMusic.isPresent());
        assertEquals("Test Song", retrievedMusic.get().getMusicTitle());
    }

    @Test
    public void testSaveAndFindAllFields() {
        // 저장
        redisMusicRepository.saveAllFields(music);

        // 조회
        String title = redisMusicRepository.findTitleById(1L);
        String originalTitle = "Test Song";
        System.out.println("title = " + title);
        assertEquals(originalTitle, title);
    }

    @Test
    public void testUpdateMusic() {
        // 저장
        redisMusicRepository.saveIdAsKey(music);

        // 업데이트
        music.setMusicTitle("Updated Song");
        redisMusicRepository.updateMusic(music);

        // 조회
        Optional<Music> updatedMusic = redisMusicRepository.findById(1L);
        assertTrue(updatedMusic.isPresent());
        assertEquals("Updated Song", updatedMusic.get().getMusicTitle());
    }

    @Test
    public void testDeleteById() {
        // 저장
        redisMusicRepository.saveIdAsKey(music);

        // 삭제
        redisMusicRepository.deleteById(1L);

        // 조회
        Optional<Music> deletedMusic = redisMusicRepository.findById(1L);
        assertFalse(deletedMusic.isPresent());
    }
}
