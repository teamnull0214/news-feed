package com.example.newsfeed.post.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.post.dto.PostCreateRequestDto;
import com.example.newsfeed.post.dto.PostCreateResponseDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/members/{memberId}/posts")
    public ResponseEntity<PostCreateResponseDto> createPost(
            @SessionAttribute(name = "member") SessionMemberDto session,
            @RequestBody PostCreateRequestDto requestDto

            /*
    feat/post-read 브랜치
    member들이 작성한 모든 글을 조회하는 메서드
    예외처리 추가하기 -> 현재 기본기능만 구현되어있음
    */
     @GetMapping
     public ResponseEntity<List<PostResponseDto>> findAllPosts(){

        List<PostResponseDto> postResponseDtoList = postService.findAll();

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK); // status 200
    }


    /*
    feat/post-read 브랜치
    postId를 통해 특정 게시물을 조회하는 메서드
    */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long postId){

        PostResponseDto postResponseDto = postService.findById(postId);

        // 성공시 status 200 실패시 stauts 404 PostRepository.java ->  findByIdOrElseThrow 메서드 참고
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK); // status 200
    }

    //feat/post-updateDelete
    // 포스트 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updateImageAndContents(
            @PathVariable Long id,
            @valid @RequestBody PostRequestDto dto
            HttpServletRequest httpServletRequest
    ) {
        Long memberId = getMemberIdBySession(httpServletRequest);
        PostResponseDto postResponseDto = postService.updateImageAndContents(id, memberId, dto);
        return ResponseEntity.ok(postService.updateImageAndContents(id, memberId, dto));
    }

    //feat/post-updateDelete
    //삭제
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
            ) {
        Long userId = getMemberIdBySession(httpServletRequest);
        postService.deleteById(id);
    }

    private Long getMemberIdBySession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        return (Long) session.getAttribute("memberId");
    }

