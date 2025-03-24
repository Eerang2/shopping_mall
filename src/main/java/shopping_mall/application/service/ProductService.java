package shopping_mall.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.domain.product.entity.CartEntity;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.domain.product.model.Cart;
import shopping_mall.domain.product.model.Product;
import shopping_mall.infrastructure.auth.repository.CartRepository;
import shopping_mall.infrastructure.auth.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

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

    @Transactional
    public void insertCart(Cart cart) {
        Optional<CartEntity> existingCart = cartRepository.findByUserIdAndProductId(cart.getUserId(), cart.getProductId());
        if (existingCart.isPresent()) {
            CartEntity cartEntity = existingCart.get();
            cartEntity.updateCart(cartEntity.getKey(), cart.getQuantity());
        } else {
            cartRepository.save(cart.toEntity());
        }
    }
}
