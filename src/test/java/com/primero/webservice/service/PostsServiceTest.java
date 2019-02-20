package com.primero.webservice.service;

import com.primero.webservice.domain.posts.Posts;
import com.primero.webservice.domain.posts.PostsRepository;
import com.primero.webservice.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void savePosts(){

        // given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("Zzzzz.co.kr@gmail.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        // when
        postsService.save(dto);

        // then
        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    @Transactional
    public void modifyPosts(){

        // given
        PostsSaveRequestDto dtoo = PostsSaveRequestDto.builder()
                .author("Zzzzz.co.kr@gmail.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        postsService.save(dtoo);

        // given2
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("Zzzzz.co.kr@gmail.com")
                .content("테스트")
                .title("Test")
                .build();

        // when
        postsService.modify(dto, 1L);

        // then
        Posts posts = postsRepository.getOne(1L);
        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void deletePosts(){

        // given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("Zzzzz.co.kr@gmail.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        postsService.save(dto);

        // when
        postsService.delete(1L);

        // then
        long cnt = postsRepository.count();
        assertThat(cnt).isEqualTo(0);
    }
}