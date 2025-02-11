package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myblog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
