package com.example.newsfeed.comment.service;

import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.createdto.CommentCreateRequestDto;
import com.example.newsfeed.comment.dto.createdto.CommentCreateResponseDto;
import com.example.newsfeed.comment.dto.updatedto.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.updatedto.CommentUpdateResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.global.annotation.LoginRequired;
import com.example.newsfeed.global.entity.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    // 댓글 생성
    @Transactional
    public CommentCreateResponseDto createComment(SessionMemberDto session,Long postId, CommentCreateRequestDto requestDto) {
        // 댓글을 다는 게시물 찾기
        Post post = postService.findPostByIdOrElseThrow(postId);
        // 로그인 본인 맞는경우 댓글 작성 가능
        Member member = new Member(session.getId());
        Comment comment = new Comment(post, member, requestDto.getCommentContents());
        commentRepository.save(comment);
        return new CommentCreateResponseDto(
                comment.getId(),
                post.getContents(),
                post.getImage(),
                session.getNickname(),
                session.getEmail(),
                comment.getCommentContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
    // 댓글수정
    @Transactional
    public CommentUpdateResponseDto updateComment(SessionMemberDto session, Long postId, Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = findCommentByIdOrElseThrow(commentId);
        // 댓글을 단 게시물 찾기
        Post post = postService.findPostByIdOrElseThrow(postId);

        if(!comment.getMember().getId().equals(session.getId())){
            throw new RuntimeException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.updateComment(requestDto.getCommentContents());
        return new CommentUpdateResponseDto(
                comment.getId(),
                post.getContents(),
                post.getImage(),
                session.getNickname(),
                session.getEmail(),
                comment.getCommentContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 댓글 조회

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByComment(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getPost().getId(),
                        comment.getMember().getNickname(),
                        comment.getMember().getEmail(),
                        comment.getCommentContents(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).collect(Collectors.toList());
    }
    // 해당 댓글 삭제
    @Transactional
    public void deleteComment(SessionMemberDto session, Long postId, Long commentId) {
        Comment comment = findCommentByIdOrElseThrow(commentId);

        if(!comment.getMember().getId().equals(postId)){
            throw new RuntimeException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    public Comment findCommentByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당 댓글이 없습니다.")
        );
    }
}
