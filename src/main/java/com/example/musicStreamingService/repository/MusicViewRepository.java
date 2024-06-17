package com.example.musicStreamingService.repository;


import com.example.musicStreamingService.entity.MusicView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicViewRepository extends JpaRepository<MusicView, Long> {
}
