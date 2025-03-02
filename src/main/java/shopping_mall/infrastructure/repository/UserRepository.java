package shopping_mall.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(String username);
}
