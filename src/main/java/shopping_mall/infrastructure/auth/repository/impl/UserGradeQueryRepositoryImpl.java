package shopping_mall.infrastructure.auth.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shopping_mall.application.service.dto.UserWithGradeDto;
import shopping_mall.domain.auth.entity.QUserEntity;
import shopping_mall.domain.policy.entity.QGradePolicyEntity;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserGradeQueryRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<UserWithGradeDto> findUserWithGrade(Long userKey) {

        QUserEntity user = QUserEntity.userEntity;
        QGradePolicyEntity grade = QGradePolicyEntity.gradePolicyEntity;

    UserWithGradeDto userWithGradeDto = jpaQueryFactory
            .select(Projections.constructor(UserWithGradeDto.class,
                    user.key,
                    user.id,
                    grade.key,
                    grade.name,
                    grade.discountRate
            ))
            .from(user)
            .leftJoin(grade).on(user.userGradeKey.eq(grade.key))
            .where(user.key.eq(userKey))
            .fetchOne();

    return Optional.ofNullable(userWithGradeDto);
    }
}
