package shopping_mall.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.application.product.repository.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void deleteOrderItemsByOrderMainKey(Long orderMainKey);
}
