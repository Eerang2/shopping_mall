package shopping_mall.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.domain.product.model.Product;
import shopping_mall.infrastructure.auth.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(Product::from)
                .toList();
    }

    public Product findProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return Product.from(productEntity);
    }
}
