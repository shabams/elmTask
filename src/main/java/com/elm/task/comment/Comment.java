package com.elm.task.comment;


import com.elm.task.article.Article;
import com.elm.task.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Comment {

    private static final int MAX_TEXT_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Text must not be empty!")
    @Length(max = MAX_TEXT_LENGTH, message = "Text should be less than: " + MAX_TEXT_LENGTH)
    @Column(name = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

}
