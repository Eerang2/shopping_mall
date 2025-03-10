package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopping_mall.domain.auth.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(String username);

    boolean findByIdAndPassword(String password, String id);
}
