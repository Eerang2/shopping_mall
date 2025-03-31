package shopping_mall.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.application.product.repository.entity.OrderMain;

import java.util.Optional;

public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {

    Optional<OrderMain> findByMerchantUid(String merchantUid);
}
