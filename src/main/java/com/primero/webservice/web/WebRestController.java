package com.primero.webservice.web;

import com.primero.webservice.service.PostsService;
import com.primero.webservice.web.dto.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsService postsService;
    private Environment env;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto){ postsService.save(dto); }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }

    @PutMapping("/posts/{pNum}")
    public void modifyPosts(@RequestBody PostsSaveRequestDto dto, @PathVariable("pNum") Long id){ postsService.modify(dto, id); }

    @DeleteMapping("/posts/{pNum}")
    public void deletePosts(@PathVariable("pNum") Long id){ postsService.delete(id); }
}