package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.product.entity.OrderEntity;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Modifying
    @Query("DELETE FROM OrderEntity o WHERE (o.status = :pendingStatus OR o.status = :rejectedStatus) AND o.createdAt < :expirationTime")
    int deleteByStatusAndCreatedAtBefore(
            @Param("pendingStatus") ApprovalStatus pendingStatus,
            @Param("rejectedStatus") ApprovalStatus rejectedStatus,
            @Param("expirationTime") LocalDateTime expirationTime
    );

    @Modifying
    @Query("DELETE FROM OrderEntity o WHERE o.merchantUid = :merchantUid")
    int deleteByMerchantUid(@Param("merchantUid") String merchantUid);
}
