package com.primero.webservice.web.dto;

import com.primero.webservice.domain.posts.Posts;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostsSaveRequestDto {

    @NotBlank(message = "제목을 작성해주세요.")
    private String title;

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

    @NotBlank(message = "작성자를 작성해주세요.")
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){

        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}