package shopping_mall.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.domain.entity.SellerEntity;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    Boolean existsById(String username);
    Optional<SellerEntity> findById(String id);
}
