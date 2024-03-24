package org.jog.gitsecuritydb.service;

import org.jog.gitsecuritydb.dto.ProductDTO;
import org.jog.gitsecuritydb.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne(ProductDTO productDTO);

    Product updateOneById(Long productId, ProductDTO productDTO);

    Product disableOneById(Long productId);
}
