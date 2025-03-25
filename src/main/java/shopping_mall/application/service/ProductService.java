package shopping_mall.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.service.dto.ProductWithQuantity;
import shopping_mall.domain.product.entity.CartEntity;
import shopping_mall.domain.product.entity.ProductEntity;
import shopping_mall.domain.product.model.Cart;
import shopping_mall.domain.product.model.Product;
import shopping_mall.infrastructure.auth.repository.CartRepository;
import shopping_mall.infrastructure.auth.repository.ProductRepository;
import shopping_mall.presentation.auth.front.user.api.dto.CartRes;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ProductWithQuantity> findAllByProducts(List<PaymentReq.CartItem> cartProducts) {
        List<Long> productIds = cartProducts.stream()
                .map(PaymentReq.CartItem::getProductId)
                .collect(Collectors.toList());

        List<ProductEntity> products = productRepository.findAllById(productIds);

        // 상품 ID별 수량 매핑
        Map<Long, Integer> quantityMap = cartProducts.stream()
                .collect(Collectors.toMap(
                        PaymentReq.CartItem::getProductId,
                        PaymentReq.CartItem::getQuantity
                ));

        // Entity -> ProductWithQuantity 변환
        return products.stream()
                .map(entity -> {
                    Product product = Product.from(entity);
                    int quantity = quantityMap.getOrDefault(entity.getKey(), 0);
                    return new ProductWithQuantity(product, quantity);
                })
                .collect(Collectors.toList());
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

    public List<CartRes> getProductsByIds(List<Long> productIds) {
        List<ProductEntity> products = productRepository.findByKeyIn(productIds);
        return products.stream()
                .map(p -> new CartRes(p.getKey(), p.getName(), p.getUniqueImagePath(), p.getPrice()))
                .collect(Collectors.toList());
    }

}
