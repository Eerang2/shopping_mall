package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping_mall.domain.auth.entity.SellerEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    Boolean existsById(String username);
    Optional<SellerEntity> findById(String id);

    List<SellerEntity> findAllByStatus(ApprovalStatus status);
}
