package org.jog.gitsecuritydb.persistence.repository;

import org.jog.gitsecuritydb.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
