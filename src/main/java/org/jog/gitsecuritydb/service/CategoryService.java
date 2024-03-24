package org.jog.gitsecuritydb.service;

import org.jog.gitsecuritydb.dto.CategoryDTO;
import org.jog.gitsecuritydb.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(CategoryDTO categoryDTO);

    Category updateOneById(Long categoryId, CategoryDTO categoryDTO);

    Category disableOneById(Long categoryId);
}
