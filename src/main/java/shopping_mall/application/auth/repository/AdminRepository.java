package shopping_mall.application.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping_mall.application.auth.repository.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Admin e WHERE e.id = :id AND e.status = 'PENDING'")
    boolean existsById(@Param("id") String id);

    @Modifying
    @Query("UPDATE Admin e SET e.status = 'APPROVED' WHERE e.id = :id")
    void updateStatusById(@Param("id") String id);

    Optional<Admin> findById(String id);


}
