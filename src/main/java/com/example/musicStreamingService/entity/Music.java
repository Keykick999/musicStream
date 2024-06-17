package com.example.musicStreamingService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Musics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long musicId;

    @Column(name = "music_title", nullable = false)
    private String musicTitle;

    @Column(name = "music_genre", nullable = false)
    private String musicGenre;

    @Column(name = "music_likes", nullable = false, columnDefinition = "int default 0")
    private int musicLikes;

    @Column(name = "music_dislikes", nullable = false, columnDefinition = "int default 0")
    private int musicDislikes;

    @Lob
    @Column(name = "music_lyrics", nullable = false)
    private String musicLyrics;

    @Column(name = "music_views", nullable = false, columnDefinition = "int default 0")
    private int musicViews;

    @Column(name = "music_path")
    private String musicPath;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Member artist;

    @OneToMany(mappedBy = "music")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "music")
    private Set<MusicFeature> musicFeatures;

    @OneToMany(mappedBy = "music")
    private Set<Like> likes;

    @OneToMany(mappedBy = "music")
    private Set<Dislike> dislikes;

    @OneToMany(mappedBy = "music")
    private Set<MusicView> musicList;

    public Music(String musicTitle, String musicGenre, String musicLyrics, String musicPath, Member artist) {
        this.musicTitle = musicTitle;
        this.musicGenre = musicGenre;
        this.musicLyrics = musicLyrics;
        this.musicPath = musicPath;
        this.artist = artist;
    }
}
