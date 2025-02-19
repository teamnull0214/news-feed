package com.example.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableJpaAuditing
@SpringBootApplication
// 페이징 출력값에서 쓸데없는 값 지워줌
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class NewsFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsFeedApplication.class, args);
    }

}
