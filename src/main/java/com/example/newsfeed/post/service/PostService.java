package com.example.newsfeed.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ID 찾을 수 없음")
        );
        post.update(dto.getImage(), dto.getContents());
        return new PostResponseDto(post.getId(), post.member(), post.image(), post.contents());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID 찾을 수 없음")
        }
        postRepository.deleteById(id);
    }

}
