package shopping_mall.application.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.application.auth.enums.ApprovalStatus;
import shopping_mall.application.auth.repository.entity.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Boolean existsById(String username);

    Optional<Seller> findById(String id);

    List<Seller> findAllByStatus(ApprovalStatus status);
}
