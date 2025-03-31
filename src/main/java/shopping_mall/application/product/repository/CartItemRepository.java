package shopping_mall.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping_mall.application.product.repository.entity.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemRepositoryCustom {

    Optional<CartItem> findByProductKeyAndCartMainKey(Long productKey, Long cartMainKey);

    List<CartItem> findByCartMainKey(Long cartMainKey);
}
