package com.example.musicStreamingService.entity;

import com.example.musicStreamingService.entity.Music;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Music_features")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MusicFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_feature_id")
    private Long musicFeatureId;

    @ManyToOne
    @JoinColumn(name = "music_id", nullable = false)
    private Music music;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
