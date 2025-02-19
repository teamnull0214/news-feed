package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.service.MemberPostSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.newsfeed.global.constant.EntityConstants.CREATED_AT;
import static com.example.newsfeed.global.constant.EntityConstants.MODIFIED_AT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/{memberId}/posts") // memberId를 기반으로 posts 리소스 조회
public class MemberPostSortController {

    private final MemberPostSortService memberPostSortService;

    /*유저 한명의 전체 게시글 조회 (수정일자 기준 최신순)*/
    @GetMapping("/sorted-by-modified")
    public ResponseEntity<Page<PostResponseDto>> findPostsSortedByModifiedAt(
            @PathVariable Long memberId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> dtoPage = memberPostSortService.findPostsSorted(memberId, startDate, endDate, page, size, MODIFIED_AT, false);
        return ResponseEntity.ok(dtoPage);
    }

    /*유저 한명의 전체 게시글 조회 (등록일자 기준 최신순)*/
    @GetMapping("/sorted-by-created")
    public ResponseEntity<Page<PostResponseDto>> findPostsSortedByCreatedAt(
            @PathVariable Long memberId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> dtoPage = memberPostSortService.findPostsSorted(memberId, startDate, endDate, page, size, CREATED_AT, false);
        return ResponseEntity.ok(dtoPage);
    }

    /* 유저 한명의 전체 게시글 조회 (좋아요 많은 순) */
    @GetMapping("/sorted-by-likes")
    public ResponseEntity<Page<PostResponseDto>> findPostsSortedByLike(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostResponseDto> dtoPage = memberPostSortService.findPostsSorted(memberId, null, null, page, size, null, false);
        return ResponseEntity.ok(dtoPage);
    }
}

