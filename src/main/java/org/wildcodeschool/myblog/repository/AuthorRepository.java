package org.wildcodeschool.myblog.repository;

import org.wildcodeschool.myblog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
