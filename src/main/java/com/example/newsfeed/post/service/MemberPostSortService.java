package com.example.newsfeed.post.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service("postSortService")
@RequiredArgsConstructor
public class MemberPostSortService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostsSorted(Long memberId, LocalDate startDate, LocalDate endDate, int page, int size, String sortBy, Boolean isLiked) {

        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(sortBy).descending());

        Page<Post> postPage;
        if (isLiked != null && isLiked) {
            postPage = postRepository.findAllByMemberIdAndLike(findMember.getId(), pageable);
        } else {
            postPage = postRepository.findAllByMemberId(findMember.getId(), startDate, endDate, pageable);
        }

        List<PostResponseDto> dtoList = postPage.getContent().stream()
                .map(PostResponseDto::toDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());
    }
}
