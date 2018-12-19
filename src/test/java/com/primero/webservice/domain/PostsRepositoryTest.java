package com.primero.webservice.domain;

import com.primero.webservice.domain.posts.Posts;
import com.primero.webservice.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        /**
         * 이후 테스트 코드에 영향을 끼치지 않기 위해
         * 테스트 메소드가 끝날 때마다 repository 전체 비우는 코드
         **/
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){

        // given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("Zzzzz.co.kr@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(), is("테스트 게시글"));
        assertThat(posts.getContent(), is("테스트 본문"));
    }

    @Test
    public void BaseTimeEntity_등록(){

        // given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                    .title("테스트 게시글")
                    .content("테스트 본문")
                    .author("Zzzzz.co.kr@gmail.com")
                    .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }

    @Test
    @Transactional
    public void 게시글_목록_불러오기(){

        // given
        Stream<Posts> postsList = postsRepository.findAllDesc();

        // when


        // then
        Iterator<Posts> iterator = postsList.iterator();
        Posts posts;

        while(iterator.hasNext()){
            posts = iterator.next();
            System.out.println(posts.getId());
            System.out.println(posts.getTitle());
            System.out.println(posts.getAuthor());
            System.out.println(posts.getContent());
        }
    }
}