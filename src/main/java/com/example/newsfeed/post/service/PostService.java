package com.example.newsfeed.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //업데이트
    @Transactional
    public PostResponseDto  updateImageAndContent(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ID 찾을 수 없음")
        );

        Users findPostMembers = findPost.getMember();

        if (!Objects.equals(userId, findPostMembers.grtId())) {
            throw new IllegalArgumentException("해당 사용자 ID 찾을 수 없음")
        }
        post.updateImageAndContent(dto.getImage(), dto.getContents());
        return new PostResponseDto(post.getId(), post.member(), post.image(), post.contents());
    }

    //삭제
    @Transactional
    public void deleteById(Long id, Long memberId) {

        Users findPostMembers = findPost.getMember();

        if (!Objects.equals(memberId, findPostMembers.getId())) {
            throw new IllegalArgumentException("해당 사용자 ID 찾을 수 없음")
        }

        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID 찾을 수 없음")
        }
        postRepository.deleteById(id);
    }

}
