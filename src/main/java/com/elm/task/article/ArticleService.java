package com.elm.task.article;

import com.elm.task.user.UserRepository;
import com.elm.task.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private  final UserService userService;
    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public Article getById(Long id) {

        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent())
            return article.get();
        return null;
    }

    public Article getByIdDisabledFalse(Long id) {

        Optional<Article> article = articleRepository.findByIdAndDisabled(id,false);
        if (article.isPresent())
            return article.get();
        return null;
    }

    public Page<Article> getAllDisabledFalse(Pageable pageable) {
        return articleRepository.findAllByDisabled(pageable,false);
    }


    public Article save(Article post) {

        post.setAuthor( userService.currentUser().get());

        return articleRepository.saveAndFlush(post);
    }


    public void delete(Article post) {

        articleRepository.delete(post);
    }

    public void updateLikes(Long id) {
        Article article= getByIdDisabledFalse(id);
        if (article!=null){
            article.setLikes(article.getLikes()+1);
        }
        articleRepository.flush();
    }

    public void updateDisLikes(Long id) {
        Article article= getByIdDisabledFalse(id);
        if (article!=null){
            article.setDisLikes(article.getDisLikes()+1);
        }
        articleRepository.flush();
    }

    public void enable(Long id) {
        Article article= getById(id);
        if (article!=null){
            article.setDisabled(false);
        }
        articleRepository.flush();
    }

    public void disable(Long id) {
        Article article= getById(id);
        if (article!=null){
            article.setDisabled(true);
        }
        articleRepository.flush();
    }
}
