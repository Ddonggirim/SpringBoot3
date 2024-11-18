package com.example.demo.service;

import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    public List<Article> index(){
        return articleRepository.findAll();
    }
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public Article create(ArticleForm dto){
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }
    public Article update(Long id, ArticleForm dto){
        // 1. dto -> entity 변환하기
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id, article.toString());
        // 2. 업데이트할 레코드 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청처리하기
        if (target == null || id != article.getId()){
            return null;
        }
        // 4. 업데이트 및 응답하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }
    public Article delete(@PathVariable Long id){
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target == null){
            return null;
        }
        //3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dtos -> Entity 변환
//        List<Article> articleList = new ArrayList<>();
//        for(int i=0; i<dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 2. DB 저장
//        for(int i=0; i<articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }
        articleList.stream().forEach(article -> articleRepository.save(article));
        // 3. 에러발생
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        // 4. 결과값 반환
        return articleList;
    }
}
/*
Post
title
content
 */