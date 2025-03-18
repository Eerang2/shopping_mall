package shopping_mall.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shopping_mall.domain.auth.enums.UserGrade;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UserWithGradeDto {

    private Long userKey;
    private String id;
    private Long gradeKey;
    private UserGrade gradeName;
    private BigDecimal gradeDiscountPrice;
}
