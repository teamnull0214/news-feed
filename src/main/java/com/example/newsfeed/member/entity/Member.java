package com.example.newsfeed.member.entity;

import com.example.newsfeed.global.entity.BaseDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "members")
public class Member extends BaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String info;

    private String mbti;

    @ColumnDefault("false")
    private boolean isDeleted;

    public Member() {

    }
}
