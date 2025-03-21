package shopping_mall.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping_mall.domain.auth.enums.UserGrade;
import shopping_mall.domain.policy.entity.GradePolicyEntity;

public interface UserGradeRepository extends JpaRepository<GradePolicyEntity, Long> {
}
