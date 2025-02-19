package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.MemberPostSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/{memberId}/posts") // memberId를 기반으로 posts 리소스 조회
public class MemberPostSortController {

    private final MemberPostSortService memberPostSortService;

     /*유저 한명의 전체 게시글 조회 (수정일자 기준 최신순)*/
    @GetMapping("/sorted-by-modified")
    public ResponseEntity<List<PostResponseDto>> findPostsSortedByModifiedAt(
            @PathVariable Long memberId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate

    ) {
        List<PostResponseDto> postResponseDtoList = memberPostSortService.findPostsSortedByModified(memberId,startDate, endDate);
        return ResponseEntity.ok(postResponseDtoList);
    }

    /*유저 한명의 전체 게시글 조회 (등록일자 기준 최신순)*/
    @GetMapping("/sorted-by-created")
    public ResponseEntity<List<PostResponseDto>> findPostsSortedByCreatedAt(
            @PathVariable Long memberId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<PostResponseDto> postResponseDtoList = memberPostSortService.findPostsSortedByCreatedAt(memberId,startDate, endDate);
        return ResponseEntity.ok(postResponseDtoList);
    }

    /* 유저 한명의 전체 게시글 조회 (좋아요 많은 순) */
    @GetMapping("/sorted-by-likes")
    public ResponseEntity<List<PostResponseDto>> findPostsSortedByLike(
            @PathVariable Long memberId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<PostResponseDto> postResponseDtoList = memberPostSortService.findPostsSortedByLIKE(memberId,startDate, endDate);
        return ResponseEntity.ok(postResponseDtoList);
    }
}

