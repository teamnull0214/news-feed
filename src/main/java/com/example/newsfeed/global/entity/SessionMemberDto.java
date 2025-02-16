package com.example.newsfeed.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@AllArgsConstructor
public class SessionMemberDto {

    private Long id;
    private String username;
    private String nickname;
    private String email;


}
