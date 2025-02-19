package com.example.newsfeed.post.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /*todo: 페이징을 통해 정렬가능하기 때문에 PostRepository에서 OrderBy 부분 삭제하면 될 것 같습니다.*/
    /*수정일 기준 전체 조회 */
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsSortedByModified(Long memberId, LocalDate startDate, LocalDate endDate) {
        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);
        return postRepository.findAllByMemberIdAndModifiedAt(findMember.getId(), startDate, endDate)
                .stream()
                .map(PostResponseDto::toDto)
                .toList();
    }

    /*todo: 페이징을 통해 정렬가능하기 때문에 PostRepository에서 OrderBy 부분 삭제하면 될 것 같습니다.*/
    /*등록일 기준 전체 조회*/
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsSortedByCreatedAt(Long memberId, LocalDate startDate, LocalDate endDate) {

        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);
        return postRepository.findAllByMemberIdAndCreatedAt(findMember.getId(), startDate, endDate)
                .stream()
                .map(PostResponseDto::toDto)
                .toList();
    }

    /*todo: 페이징만 진행하시면 될 것 같습니다.*/
    /*좋아요 기준 전체 조회 */
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsSortedByLIKE(Long memberId, LocalDate startDate, LocalDate endDate) {

        Member findMember = memberService.findActiveMemberByIdOrElseThrow(memberId);
        return postRepository.findAllByMemberIdAndLike(findMember.getId(), startDate, endDate)
                .stream()
                .map(PostResponseDto::toDto)
                .toList();
    }
}
