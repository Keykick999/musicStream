package com.example.musicStreamingService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Members")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id", length = 320)
    private String memberId;

    @Column(name = "member_password", nullable = false, length = 60)
    private String memberPassword;

    @Column(name = "member_contact", nullable = false, unique = true, length = 15)
    private String memberContact;

    @Column(name = "member_subsribers", nullable = false)
    private int memberSubscribers;

    @Column(name = "member_role", length = 6)
    private String memberRole;

    @OneToMany(mappedBy = "member")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "member")
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "member")
    private Set<Reply> replies;

    @OneToMany(mappedBy = "member")
    private Set<Like> likes;

    @OneToMany(mappedBy = "member")
    private Set<Dislike> dislikes;

    @OneToMany(mappedBy = "member")
    private Set<MusicView> musicViews;

    @OneToMany(mappedBy = "artist")
    private Set<Music> musics;
}
