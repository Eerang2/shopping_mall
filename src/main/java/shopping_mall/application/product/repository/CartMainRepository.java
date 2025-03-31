package shopping_mall.application.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping_mall.application.product.repository.entity.CartMain;

import java.util.Optional;

@Repository
public interface CartMainRepository extends JpaRepository<CartMain, Long> {

    Optional<CartMain> findByUserKey(Long userKey);

}
