package com.example.musicStreamingService.redisRepository;

import com.example.musicStreamingService.entity.Member;
import com.example.musicStreamingService.entity.Music;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RedisMusicRepository {

    private final RedisTemplate<String, Music> redisTemplate;

    public RedisMusicRepository(RedisTemplate<String, Music> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /** 저장 **/
    //id key 값으로 저장
    public void saveIdAsKey(Music music) {
        String key = "musicId:" + music.getMusicId();
        redisTemplate.opsForValue().set(key, music);
    }

    //title key 값으로 저장
    public void saveTitleAsKey(Music music) {
        String key = "musicTitle:" + music.getMusicTitle();
        redisTemplate.opsForValue().set(key, music);
    }

    //전체 필드 다 저장? (공간 낭비인 것 같긴함...)
    public void saveAllFields(Music music) {
        String key = "music:" + music.getMusicId();
        Map<String, Object> musicMap = new HashMap<>();
        musicMap.put("musicTitle", music.getMusicTitle());
        musicMap.put("musicGenre", music.getMusicGenre());
        musicMap.put("musicLikes", music.getMusicLikes());
        musicMap.put("musicDislikes", music.getMusicDislikes());
        musicMap.put("musicLyrics", music.getMusicLyrics());
        musicMap.put("musicViews", music.getMusicViews());
        musicMap.put("artistId", music.getArtist().getMemberId());

        redisTemplate.opsForHash().putAll(key, musicMap);
    }




    /** 조회 **/
    public Optional<Music> findById(Long musicId) {
        Music music = redisTemplate.opsForValue().get("musicId:" + musicId);
        return Optional.ofNullable(music);
    }


    //id로 제목 조회
    public String findTitleById(Long musicId) {
        String key = "music:" + musicId;
        return (String) redisTemplate.opsForHash().get(key, "musicTitle");
    }


    public Optional<Music> findByTitle(String musicTitle) {
        String key = "musicTitle:" + musicTitle;
        Music findMusic = redisTemplate.opsForValue().get(key);

        return Optional.of(findMusic);
    }

    /** 변경 **/
    public void updateMusic(Music music) {
        saveIdAsKey(music);
    }

    /** 삭제 **/
    public void deleteById(Long musicId) {
        String key = "music:" + musicId;
        redisTemplate.delete(key);
    }
}
