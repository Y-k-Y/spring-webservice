package com.primero.webservice;

import com.primero.webservice.domain.posts.PostsRepository;
import com.primero.webservice.web.dto.PostsMainResponseDto;
import com.primero.webservice.web.dto.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostsService {

    private PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){

        return postsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public List<PostsMainResponseDto> findAllDesc(){

        return postsRepository.findAllDesc()
                    .map(PostsMainResponseDto::new)
                    .collect(Collectors.toList());


    }
}