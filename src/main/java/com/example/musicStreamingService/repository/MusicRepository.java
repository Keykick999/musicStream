package com.example.musicStreamingService.repository;

import com.example.musicStreamingService.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    @Query("select m from Music m where m.artist.memberId = :memberId")
    List<Music> findByMemberId(@Param("memberId") String memberId);
}
