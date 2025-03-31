package shopping_mall.application.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.repository.UserRepository;
import shopping_mall.application.auth.repository.entity.User;
import shopping_mall.application.product.repository.CartItemRepository;
import shopping_mall.application.product.repository.CartMainRepository;
import shopping_mall.application.product.repository.OrderItemRepository;
import shopping_mall.application.product.repository.OrderMainRepository;
import shopping_mall.application.product.repository.entity.CartMain;
import shopping_mall.application.product.repository.entity.OrderItem;
import shopping_mall.application.product.repository.entity.OrderMain;
import shopping_mall.application.product.repository.entity.enums.OrderStatus;
import shopping_mall.application.product.service.dto.CartItemDto;
import shopping_mall.application.product.service.dto.CheckRes;
import shopping_mall.presentation.payment.dto.PaymentReq;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CartItemRepository cartItemRepository;
    private final CartMainRepository cartMainRepository;
    private final UserRepository userRepository;
    private final OrderMainRepository orderMainRepository;
    private final OrderItemRepository orderItemRepository;

    public CheckRes checkout(PaymentReq paymentReq, Long userKey) {
        // 유저 장바구니에 있는 데이터 추출
        CartMain cartMain = cartMainRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지습니다."));

        // 2️⃣ QueryDSL로 장바구니 상품 + 가격 한 번에 조회
        List<CartItemDto> cartItems = cartItemRepository.findCartItemsWithProduct(cartMain.getKey());

        Map<Long, CartItemDto> cartProductMap = cartItems.stream()
                .collect(Collectors.toMap(CartItemDto::getProductKey, item -> item));

        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;

        for (PaymentReq.Products product : paymentReq.getProducts()) {
            CartItemDto cartItem = cartProductMap.get(product.getProductKey());

            // request 데이터와 DB 데이터 비교
            validateCartItem(cartItem, product);

            calculatedTotalPrice = calculatedTotalPrice.add(
                    cartItem.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()))
            );
        }
        // 최종 가격 비교
        if (calculatedTotalPrice.compareTo(paymentReq.getTotalPrice()) != 0) {
            throw new IllegalArgumentException("총 결제 금액이 올바르지 않습니다.");
        }
        User user = userRepository.findById(userKey)
                .orElseThrow(IllegalArgumentException::new);

        return CheckRes.of("장바구니 상품", calculatedTotalPrice, user.getName());
    }

    private void validateCartItem(CartItemDto cartItem, PaymentReq.Products product) {
        if (cartItem == null) {
            throw new IllegalArgumentException("요청한 상품이 장바구니에 없습니다.");
        }

        if (cartItem.getQuantity() != product.getQuantity()) {
            throw new IllegalArgumentException("상품 수량이 일치하지 않습니다.");
        }
    }

    @Transactional
    public void insertOrder(CheckRes res, PaymentReq req, Long userKey, String merchantUid) {

        // 🛒 유저 장바구니 가져오기
        CartMain cartMain = cartMainRepository.findByUserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        // 📝 주문 메인 저장 (임시 상태)
        OrderMain order = OrderMain.builder()
                .cartMainKey(cartMain.getKey())
                .userKey(userKey)
                .merchantUid(merchantUid)
                .orderStatus(OrderStatus.READY)
                .totalPrice(res.getAmount()) // 🏷 총 가격 저장
                .build();
        orderMainRepository.save(order);

        // 🔍 QueryDSL로 장바구니 상품 + 가격 한 번에 조회
        List<CartItemDto> cartItems = cartItemRepository.findCartItemsWithProduct(cartMain.getKey());

        // ✅ 장바구니 상품을 Map으로 변환 (빠른 조회)
        Map<Long, CartItemDto> cartProductMap = cartItems.stream()
                .collect(Collectors.toMap(CartItemDto::getProductKey, item -> item));

        BigDecimal calculatedTotalPrice = BigDecimal.ZERO; // 🔹 검증용 총 가격

        // 🛍 주문 아이템 저장
        for (PaymentReq.Products product : req.getProducts()) {
            CartItemDto cartItem = cartProductMap.get(product.getProductKey());

            if (cartItem == null) {
                throw new IllegalArgumentException("장바구니에 없는 상품이 포함되었습니다.");
            }

            BigDecimal totalItemPrice = cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            // 🛒 상품 가격 계산

            calculatedTotalPrice = calculatedTotalPrice.add(totalItemPrice);

            // 📌 주문 아이템 저장
            OrderItem orderItem = OrderItem.builder()
                    .orderMainKey(order.getKey())
                    .cartItemKey(cartItem.getCartItemKey())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .createdAt(LocalDateTime.now())
                    .build();

            orderItemRepository.save(orderItem);

        }
    }

    @Transactional
    public void cancelOrder(String merchantUid) {
        OrderMain orderMain = findByMerchantUid(merchantUid);

        orderMain.setOrderStatus(OrderStatus.CANCELLED);
        orderMainRepository.save(orderMain);
        orderItemRepository.deleteOrderItemsByOrderMainKey(orderMain.getKey());
    }

    public BigDecimal getOrderPriceByMerchantUid(String merchantUid) {
        OrderMain orderMain = findByMerchantUid(merchantUid);
        return orderMain.getTotalPrice();
    }

    public void updateOrderStatus(String merchantUid) {
        OrderMain orderMain = findByMerchantUid(merchantUid);
        orderMain.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
        orderMainRepository.save(orderMain);
    }

    private OrderMain findByMerchantUid(String merchantUid) {
        return orderMainRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("주문번호가 없습니다."));
    }
}
