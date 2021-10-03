package com.elm.task.article;


import com.elm.task.comment.Comment;
import com.elm.task.comment.CommentService;
import com.elm.task.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
    }





    @PreAuthorize("hasAnyAuthority('USERS')")
    @PostMapping
    public Article createArticle(@RequestBody @Valid Article article){
//        if (article.getImage().length>=500*1024) {
//            throw new ValidationException("image size should be less than 500kb");
//        }

        return  articleService.save(article);
    }


    @PreAuthorize("hasAnyAuthority('USERS')")
    @PostMapping("/{id}/comment")
    public Comment createComment(@RequestBody @Valid Comment comment,@PathVariable Long id){
        Article article = articleService.getByIdDisabledFalse(id);
        if (article==null){
            throw new IllegalArgumentException(id+" article can not be found");
        }
        comment.setArticle(article);
        return commentService.save(comment);


    }

    @GetMapping("/{id}/comment")
    public List<Comment> getCommentsOfArticle(@PathVariable Long id){

        return commentService.findAllByArticleId(id);


    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id ) {
        Article article = articleService.getByIdDisabledFalse(id);
        return article;
    }

    @GetMapping
    public Page<Article> getArticles(Pageable pageable ) {
        return articleService.getAllDisabledFalse( pageable);
    }

    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getArticleImage(@PathVariable Long id ) {
            Article article = articleService.getByIdDisabledFalse(id);
           return article ==null ? new byte[0] : article.getImage();
        //return articleService.getById(id).orElseGet(()->null);
    }

    @PreAuthorize("hasAnyAuthority('USERS')")
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article!=null) {
            if ( userService.currentUser().get().getUsername().equals(article.getAuthor().getUsername())) {
                articleService.delete(article);
            }
        }
    }


    @PreAuthorize("hasAnyAuthority('USERS')")
    @PutMapping("/{id}/likes")
    public void updateLikes(@PathVariable Long id) {
         articleService.updateLikes(id);
    }

    @PreAuthorize("hasAnyAuthority('USERS')")
    @PutMapping("/{id}/dislikes")
    public void updateDislikes(@PathVariable Long id) {
        articleService.updateDisLikes(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable Long id) {
         articleService.enable(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable Long id) {
        articleService.disable(id);
    }


}
