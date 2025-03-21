package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping_mall.domain.product.entity.StockEntity;

@Repository
public interface ProductStockRepository extends JpaRepository<StockEntity, Long> {
}
