package shopping_mall.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.domain.entity.UserEntity;
import shopping_mall.domain.enums.Grade;
import shopping_mall.domain.enums.Role;
import shopping_mall.domain.enums.UserStatus;
import shopping_mall.domain.model.User;
import shopping_mall.infrastructure.repository.UserRepository;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkId(String id) {
        return  userRepository.findById(id).isPresent();
    }

    @Transactional
    public void register(User user) {
        // 밸리데이션
        user.validate();

        // 패스워드 암호화
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode(user.getPassword());

        // 기본이넘 설정
        User saveUser = User.of(user.getId(), encodePwd);

        // 저장
        userRepository.save(saveUser.toEntity());
    }

}
