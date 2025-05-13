package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wildcodeschool.myblog.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
