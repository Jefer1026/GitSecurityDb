package org.jog.gitsecuritydb.persistence.repository;

import org.jog.gitsecuritydb.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
