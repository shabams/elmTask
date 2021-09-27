package com.elm.task.comment;

import com.elm.task.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private  final UserService userService;


    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }


    public Comment save(Comment comment) {
        comment.setUser( userService.currentUser().get());
        return commentRepository.saveAndFlush(comment);

    }


    public List<Comment> findAllByArticleId(Long id) {
        return commentRepository.findAllByArticleIdAndArticleDisabled(id,false);
    }
}
