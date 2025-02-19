package com.example.newsfeed.post.service;

import com.example.newsfeed.global.dto.SessionMemberDto;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.entity.Image;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.ImageRepository;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.post.dto.PostResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.newsfeed.global.constant.EntityConstants.MODIFIED_AT;

@Slf4j
@Service("postService")
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public PostResponseDto createPost(SessionMemberDto session, PostRequestDto dto, MultipartFile image) throws IOException{

        Member member = new Member(session.getId());

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = saveImage(image);
        }

        Post post = new Post(dto.getContents(), imagePath, member);
        postRepository.save(post);

        log.info("게시물 생성 성공");
        return PostResponseDto.toDto(post, session);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllPost(int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostResponseDto> dtoList = postPage.getContent().stream()
                .map(PostResponseDto::toDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long postId) {
        Post findPost = findPostByIdOrElseThrow(postId);
        return PostResponseDto.toDto(findPost);
    }

    /* 수정일 조건 기준 게시글 전체조회 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostsSortedByModifiedAt(LocalDate startDate, LocalDate endDate, int page, int size) {

        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(MODIFIED_AT).descending());
        Page<Post> postPage = postRepository.findAllByModifiedAt(startDate, endDate, pageable);

        List<PostResponseDto> dtoList = postPage.getContent().stream()
                .map(PostResponseDto::toDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());
    }

    @Transactional
    public PostResponseDto updatePostImageAndContents(Long postId, Long memberId, PostRequestDto dto) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("다른사람이 작성한 게시물이라 수정못함");
        }
//
//        if (dto() != null) {
//            findPost.updateImage(dto.getImage());
//        }
        if (dto.getContents() != null) {
            findPost.updateContents(dto.getContents());
        }
        return PostResponseDto.toDto(findPost);
    }

    //삭제
    @Transactional
    public void deletePost(Long postId, Long memberId) {

        Post findPost = findPostByIdOrElseThrow(postId);
        Member findPostMembers = findPost.getMember();

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("해당 사용자 ID 찾을 수 없음");
        }
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("해당 ID 찾을 수 없음");
        }

        postRepository.deleteById(postId);
    }

    public Post findPostByIdOrElseThrow(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("해당 ID 찾을 수 없음")
        );
    }

    private String saveImage(MultipartFile image) throws IOException {
        String uploadDir = "uploads/"; // 저장 폴더
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        Files.createDirectories(filePath.getParent()); // 폴더 없으면 생성
        image.transferTo(filePath.toFile()); // 파일 저장

        return filePath.toString(); // 저장된 경로 반환
    }

    String fileDir = "파일경로";
    public PostResponseDto upload(SessionMemberDto session, MultipartFile file) throws IOException{

        Member member = new Member(session.getId());

        log.info("원래 파일이름: {}");

//        Post post = new Post(dto.getContents(), dto.getContents(), member);
//        postRepository.save(post);
//
//        if (!Objects.isNull(file)) {
//            String originalFilename = file.getOriginalFilename();
//            log.info("원래 파일이름: {}", originalFilename);
//
//            String imagePath = fileDir + originalFilename;
//            log.info("원래 파일이름: {}", imagePath);
//
//            Image image = new Image(imagePath, post);
//            imageRepository.save(image);
//
//            file.transferTo(new File(imagePath));
//        }
//
//        return PostResponseDto.toDto(post);
        return null;
    }
}
