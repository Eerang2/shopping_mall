package shopping_mall.application.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.product.repository.CartItemRepository;
import shopping_mall.application.product.repository.CartMainRepository;
import shopping_mall.application.product.repository.ProductRepository;
import shopping_mall.application.product.repository.entity.CartItem;
import shopping_mall.application.product.repository.entity.CartMain;
import shopping_mall.application.product.repository.entity.Product;
import shopping_mall.presentation.cart.dto.CartRes;
import shopping_mall.presentation.payment.dto.CartProductRes;
import shopping_mall.presentation.payment.dto.ProductOrderRequest;
import shopping_mall.presentation.product.dto.ProductReq;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartMainRepository mainRepository;
    private final CartItemRepository cartItemRepository;

    /**
     * 장바구니 조회
     * 장바구니가 없으면 장바구니 생성
     *
     * @param userKey
     * @return
     */
    @Transactional
    public Long getOrCreateCartMain(Long userKey) {
        return mainRepository.findByUserKey(userKey)
                .map(CartMain::getKey)
                .orElseGet(() -> createCart(userKey));
    }


    /**
     * 장바구니에 상품 담는 로직
     * 존재하는 상품시 수량 업데이트
     *
     * @param cartItem
     * @param cartMainKey
     */
    @Transactional
    public void insertItems(CartItem cartItem, Long cartMainKey) {
        sameItem(cartMainKey, cartItem.getProductKey())
                .ifPresentOrElse(
                        existingItem -> updateExistingCartItem(existingItem, cartItem.getQuantity()),
                        () -> createNewCartItem(cartItem, cartMainKey)
                );
    }

    /**
     * 장바구니에 담긴 상품조회
     *
     * @param userKey
     * @return
     */
    public List<CartRes> findCartItems(Long userKey) {
        CartMain cartMain = findCartMainByUserKey(userKey);
        List<CartItem> cartItems = cartItemRepository.findByCartMainKey(cartMain.getKey());
        List<Product> products = findProductsByCartItems(cartItems);

        return createCartResponseList(cartItems, products);
    }


    /**
     * 장바구니에서 선택한 상품데이터 조회와 최종 가격 계산
     *
     * @param productOrderRequests
     * @param userKey
     * @return
     */
    public CartProductRes findAllByCartProducts(List<ProductOrderRequest> productOrderRequests, Long userKey) {
        //  cartMainKey 찾기
        Long cartMainKey = findCartMainByUserKey(userKey).getKey();

        //  cartItem 찾기
        List<CartItem> cartItems = cartItemRepository.findByCartMainKey(cartMainKey);

        // cartItem 기반으로 Product 조회 (선택된 상품만)
        List<Product> selectedProducts = findProductsByCartItems(cartItems).stream()
                .filter(product -> productOrderRequests.stream()
                        .anyMatch(req -> req.getProductKey().equals(product.getKey())))
                .collect(Collectors.toList());

        //  Product -> CartProduct 변환
        List<CartProductRes.CartProduct> cartProducts = selectedProducts.stream()
                .map(product -> {
                    // 선택된 상품에 대한 수량 찾기
                    int quantity = productOrderRequests.stream()
                            .filter(req -> req.getProductKey().equals(product.getKey()))
                            .findFirst()
                            .map(ProductOrderRequest::getQuantity)
                            .orElse(0); // 기본값 0

                    return CartProductRes.CartProduct.builder()
                            .productKey(product.getKey())
                            .productName(product.getName())
                            .quantity(quantity)
                            .build();
                })
                .collect(Collectors.toList());

        // 5️⃣ 총 가격 계산 (선택된 상품만 반영)
        BigDecimal totalPrice = calcFinalPrice(selectedProducts, productOrderRequests);

        // 6️⃣ 최종 결과 반환
        return new CartProductRes(cartProducts, totalPrice);
    }

    /**
     * 장바구니에서 수량 증가와 감소시 DB 에 request에 담긴 수량의 수만큼 DB 업데이트
     *
     * @param req
     * @param userKey
     */
    public void productQuantityUpdate(ProductReq.QuantityUpdate req, Long userKey) {
        CartMain cartMain = findCartMainByUserKey(userKey);
        CartItem cartItem = findCartItem(req.getProductKey(), cartMain.getKey());

        updateCartItemQuantity(cartItem, req.getChangeQuantity());

    }


    // 장바구니 생성
    private Long createCart(Long userKey) {
        return mainRepository.save(new CartMain(userKey)).getKey();
    }

    // 상품을 장바구니상품테이블에 저장하는 메서드
    private void createNewCartItem(CartItem cartItem, Long cartMainKey) {
        CartItem newCartItem = CartItem.builder()
                .productKey(cartItem.getProductKey())
                .quantity(cartItem.getQuantity())
                .cartMainKey(cartMainKey)
                .ordered(false)
                .build();
        cartItemRepository.save(newCartItem);
    }

    // 동일 상품 여부 확인하는 메서드
    private Optional<CartItem> sameItem(Long cartMainKey, Long productKey) {
        return cartItemRepository.findByProductKeyAndCartMainKey(productKey, cartMainKey);
    }

    // 동일 상품 수량 업데이트하는 메서드
    private void updateExistingCartItem(CartItem existingItem, int additionalQuantity) {
        existingItem.updateQuantity(existingItem.getQuantity() + additionalQuantity);
        cartItemRepository.save(existingItem);
    }

    // 최종가격 계산 메서드
    private BigDecimal calcFinalPrice(List<Product> products, List<ProductOrderRequest> productOrderRequests) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Product product : products) {
            int quantity = productOrderRequests.stream()
                    .filter(req -> req.getProductKey().equals(product.getKey()))
                    .findFirst()
                    .map(ProductOrderRequest::getQuantity)
                    .orElse(0); // 기본값 0

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        return totalPrice;
    }

    // 유저 pk로 메인장바구니 조회
    private CartMain findCartMainByUserKey(Long userKey) {
        return mainRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
    }

    // 장바구니에 담긴상품들의 정보 추출
    private List<Product> findProductsByCartItems(List<CartItem> cartItems) {
        List<Long> productKeys = cartItems.stream()
                .map(CartItem::getProductKey)
                .collect(Collectors.toList());
        return productRepository.findByKeyIn(productKeys);
    }

    // 장바구니 응답 리스트 생성
    private List<CartRes> createCartResponseList(List<CartItem> cartItems, List<Product> products) {
        // cartItems를 스트림으로 순회하면서 각 CartItem에 대해 Product 정보를 찾아 CartRes 객체로 변환
        return cartItems.stream()
                .map(cartItem -> {
                    Product product = findProductFromList(products, cartItem.getProductKey());
                    return CartRes.of(product, cartItem.getQuantity());
                })
                .toList();
    }

    // 주어진 상품 목록에서 특정 상품을 찾는 메소드
    private Product findProductFromList(List<Product> products, Long productKey) {
        return products.stream()
                .filter(p -> p.getKey().equals(productKey)) // 상품의 키가 일치하는 상품을 필터링
                .findFirst() // 첫 번째 일치하는 상품을 찾음
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    // 주어진 cartMainKey와 productKey로 장바구니 항목을 찾는 메소드
    private CartItem findCartItem(Long productKey, Long cartMainKey) {
        return cartItemRepository.findByProductKeyAndCartMainKey(productKey, cartMainKey)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 상품이 없습니다."));
    }

    // 장바구니 항목의 수량을 수정하는 메소드
    private void updateCartItemQuantity(CartItem cartItem, int changeQuantity) {
        // 기존 수량에 변경 수량을 더한 새로운 수량 계산
        int newQuantity = cartItem.getQuantity() + changeQuantity;

        if (newQuantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            CartItem updatedCartItem = new CartItem(
                    cartItem.getKey(),
                    cartItem.getProductKey(),
                    newQuantity,
                    cartItem.getCartMainKey()
            );
            cartItemRepository.save(updatedCartItem);
        }
    }
}
