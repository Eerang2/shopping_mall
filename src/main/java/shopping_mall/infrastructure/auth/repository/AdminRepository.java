package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping_mall.domain.auth.entity.AdminEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM AdminEntity e WHERE e.id = :id AND e.status = 'PENDING'")
    boolean existsById(@Param("id") String id);

    @Modifying
    @Query("UPDATE AdminEntity e SET e.status = 'APPROVED' WHERE e.id = :id")
    void updateStatusById(@Param("id") String id);

    Optional<AdminEntity> findById(String id);


}
