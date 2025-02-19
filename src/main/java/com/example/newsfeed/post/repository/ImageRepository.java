package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
