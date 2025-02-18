package com.example.newsfeed.member.entity;

import com.example.newsfeed.global.entity.BaseDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor
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

    // 로그인에 사용
    public Member(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Member(Long id){
        this.id = id;
    }

    // Member 프로필 수정
    // info, mbti 따로 업데이트 할 수 있게 만들어주는 조건문에 필요한 메서드
    public void updateInfo(String info) {
        this.info = info;
    }
    public void updateMbti(String mbti) {
        this.mbti = mbti;
    }
    public void updateUsernameAndNickname(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateIsDeleteTrueAndNickname() {
        this.nickname = "삭제된 유저입니다";
        this.isDeleted = true;
    }
}
