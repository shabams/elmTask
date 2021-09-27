package com.elm.task.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findAllByOrderByCreatedAtDesc();

    Page<Article> findAllByDisabled(Pageable pageable, boolean b);

    Optional<Article> findByIdAndDisabled(Long id, boolean b);
}
