package com.primero.webservice.web;

import com.primero.webservice.PostsService;
import com.primero.webservice.domain.posts.PostsRepository;
import com.primero.webservice.web.dto.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsRepository postsRepository;
    private PostsService postsService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto){ postsService.save(dto); }
}