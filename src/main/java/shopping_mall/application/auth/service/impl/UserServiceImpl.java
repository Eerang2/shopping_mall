package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.service.dto.AuthUser;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.domain.auth.entity.UserEntity;
import shopping_mall.application.auth.service.exception.LoginValidException;
import shopping_mall.domain.auth.model.User;
import shopping_mall.infrastructure.auth.repository.UserRepository;
import shopping_mall.infrastructure.util.JwtUtil;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService<User> {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkId(String id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    @Transactional
    public void register(User user) {
        // 밸리데이션
        user.validate();

        // 패스워드 암호화
        String encodePwd = passwordEncoder.encode(user.getPassword());

        // 기본이넘 설정
        User saveUser = User.of(user.getId(), encodePwd);

        // 저장
        userRepository.save(saveUser.toEntity());
    }

    @Override
    @Transactional
    public String login(User user) {

        // 존재 여부 체크
        UserEntity entity = userRepository.findById(user.getId())
                .orElseThrow(LoginValidException::new);
        if (!passwordEncoder.matches(user.getPassword(), entity.getPassword())) {
            throw new LoginValidException();
        }

        AuthUser authUser = AuthUser.of(entity.getKey(), entity.getId(), entity.getRole());
        // 토큰 생성
        return jwtUtil.createAccessToken(authUser);
    }
}
