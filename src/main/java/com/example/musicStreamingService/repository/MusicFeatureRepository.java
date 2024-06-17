package com.example.musicStreamingService.repository;

import com.example.musicStreamingService.entity.MusicFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicFeatureRepository extends JpaRepository<MusicFeature, Long> {
}
