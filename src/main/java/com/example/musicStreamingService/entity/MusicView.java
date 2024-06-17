package com.example.musicStreamingService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Music_views")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MusicView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_view_id")
    private Long musicViewId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    @Column(name = "music_views", nullable = false)
    private int musicViews;
}
