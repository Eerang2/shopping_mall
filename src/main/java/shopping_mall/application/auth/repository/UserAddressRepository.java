package shopping_mall.application.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.application.auth.repository.entity.UserShippingAddress;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserShippingAddress, Long> {

    Optional<UserShippingAddress> findByUserKey(Long userKey);
}
