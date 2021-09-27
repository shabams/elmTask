package com.elm.task.article;


import com.elm.task.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

@Data
@Entity
@Table
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Article {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_BODY_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @NotBlank(message = "Title must not be empty!")
    @Length(max = MAX_TITLE_LENGTH, message = "title should be less than: " + MAX_TITLE_LENGTH )
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Body must not be empty!")
    @Length(max = MAX_BODY_LENGTH, message = "Body should be less than: " + MAX_BODY_LENGTH)
    @Column(name = "body")
    private String body;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;


    @NotNull(message = "Creation Date must not be empty")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @NotNull
    @Lob
    @Column(name = "image", columnDefinition = "BLOB") // for size we can try length = 500000
    private byte[] image;

    @Column(name = "likes")
    @NotNull
    private Integer likes;

    @Column(name = "disLikes")
    @NotNull
    private Integer disLikes;

    @Column(name = "disabled")
    @NotNull
    private Boolean disabled;

//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", body='" + body + '\'' +
//                ", author=" + author +
//                ", createdAt=" + createdAt +
//                ", image=" + Arrays.toString(image) +
//                ", likes=" + likes +
//                ", disLikes=" + disLikes +
//                ", disabled=" + disabled +
//                '}';
//    }
}
