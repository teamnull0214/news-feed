package com.example.newsfeed.comment.service;

import com.example.newsfeed.comment.dto.CommentRequestDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.global.exception.ErrorCode;
import com.example.newsfeed.global.exception.custom.ForbiddenException;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.constant.EntityConstants.MODIFIED_AT;
import static com.example.newsfeed.global.exception.ErrorCode.CANNOT_UPDATE_OTHERS_DATA;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(SessionMemberDto session, Long postId, CommentRequestDto requestDto) {
        // 댓글을 다는 게시물 찾기
        Post post = postService.findPostByIdOrElseThrow(postId);

        Member member = new Member(session.getId());
        Comment comment = new Comment(post, member, requestDto.getCommentContents());
        commentRepository.save(comment);
        return CommentResponseDto.toDto(comment, session);
    }

    // 댓글 페이징
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> findCommentsOnPost(Long postId, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageable);

        List<CommentResponseDto> dtoList = commentPage.getContent().stream()
                .map(CommentResponseDto::toDto).toList();

        // 사용 매개변수: list, pageable(page,size,정렬기준 설정), 전체 요소 크기
        return new PageImpl<>(dtoList, pageable, commentPage.getTotalElements());
    }

    // 댓글수정
    @Transactional
    public CommentResponseDto updateComment(SessionMemberDto session, Long commentId, CommentRequestDto requestDto) {
        Comment comment = findCommentByIdOrElseThrow(commentId);

        if (!comment.getMember().getId().equals(session.getId())) {
            throw new ForbiddenException.MemberAccessDeniedException(CANNOT_UPDATE_OTHERS_DATA);
        }

        comment.updateComment(requestDto.getCommentContents());
        return CommentResponseDto.toDto(comment, session);
    }

    // 해당 댓글 삭제
    @Transactional
    public void deleteComment(SessionMemberDto session, Long commentId) {
        Comment comment = findCommentByIdOrElseThrow(commentId);

        if (!comment.getMember().getId().equals(session.getId())){
            throw new ForbiddenException.MemberAccessDeniedException(CANNOT_UPDATE_OTHERS_DATA);
        }
        commentRepository.delete(comment);
    }

    public Comment findCommentByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당 댓글이 없습니다.")
        );
    }


}
