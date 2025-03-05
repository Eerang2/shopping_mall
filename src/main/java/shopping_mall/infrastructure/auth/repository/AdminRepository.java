package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping_mall.domain.auth.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM AdminEntity e WHERE e.id = :id AND e.status = 'PENDING'")
    boolean existsById(@Param("id") String id);
}
