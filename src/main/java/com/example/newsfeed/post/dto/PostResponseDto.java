package com.example.newsfeed.post.dto;

import lombok.Getter;
import java.time.LocalDateTime;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.member.entity.Member;



@Getter
public class PostResponseDto {

    // feat/post-create 브랜치에서 만든 변수
    private final Long id;
    private final String nickname;
    private final String image;
    private final String contents; // 누락된 contents 추가
    private final int likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    /*
    feat/post-read 브랜치
    PostResponseDto 생성자
    */
    public PostResponseDto(Long id, String nickname, String contents, String image, int likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.nickname = nickname;
        this.contents = contents;
        this.image = image;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


    /*
    feat/post-read 브랜치
    JpaRepository에 전달할 Dto
    PostService.java -> public List<PostResponseDto> findAll() 메서드에서 해당 메서드 호출
    */
    public static PostResponseDto toDto(Post post){
        Member writer = post.getMember(); // Post 클래스의 멤버변수 private Member member에 접근!!
        return new PostResponseDto(
                post.getId(),
                writer.getNickname(),
                post.getContents(),
                post.getImage(),
                post.getPostLikeList().size(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

}
