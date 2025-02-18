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

    public Member(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public Member(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateIsDeleteTrueAndNickname() {
        this.nickname = "삭제된 유저입니다";
        this.isDeleted = true;
    }

    public void profileUpdate(String username, String nickname, String info, String mbti){
        this.username = username;
        this.nickname = nickname;
        this.info = info;
        this.mbti = mbti;
    }

    public Member(Long id){
        this.id = id;
    }

    public static Member fromMemberId(Long id){
        return new Member(id);
    }

    // info, mbti 따로 업데이트 할 수 있게 만들어주는 조건문에 필요한 메서드(2개)
    public void updateInfo(String info) {
        this.info = info;
    }

    public void updateMbti(String mbti) {
        this.mbti = mbti;
    }


}
