package shopping_mall.application.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_mall.application.product.repository.ProductRepository;
import shopping_mall.application.product.repository.entity.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 단일 상품 조회
     *
     * @param productKey
     * @return
     */
    public Product findById(Long productKey) {
        return productRepository.findById(productKey)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    /**
     * 상품 리스트 조회
     *
     * @return
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
