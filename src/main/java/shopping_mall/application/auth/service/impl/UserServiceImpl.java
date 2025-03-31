package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.repository.UserAddressRepository;
import shopping_mall.application.auth.repository.UserRepository;
import shopping_mall.application.auth.repository.entity.User;
import shopping_mall.application.auth.repository.entity.UserShippingAddress;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.application.auth.service.dto.AuthUser;
import shopping_mall.application.auth.service.exception.LoginValidException;
import shopping_mall.infrastructure.util.JwtUtil;

import java.util.Optional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService<User> {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserAddressRepository addressRepository;

    @Override
    public boolean checkId(String id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    @Transactional
    public void register(User user) {

        // 패스워드 암호화
        String encodePwd = passwordEncoder.encode(user.getPassword());

        // 기본이넘 설정
        User saveUser = User.of(user.getId(), user.getName(), user.getPhoneNumber(), encodePwd);

        // 저장
        userRepository.save(saveUser);
    }

    @Override
    @Transactional
    public String login(User user) {

        // 존재 여부 체크
        User entity = userRepository.findById(user.getId())
                .orElseThrow(LoginValidException::new);
        if (!passwordEncoder.matches(user.getPassword(), entity.getPassword())) {
            throw new LoginValidException();
        }

        AuthUser authUser = AuthUser.of(entity.getKey(), entity.getId(), entity.getRole());
        // 토큰 생성
        return jwtUtil.createAccessToken(authUser);
    }

    public User findUser(Long userKey) {
        return userRepository.findById(userKey).orElseThrow(LoginValidException::new);
    }

    public UserShippingAddress userAddress(Long userKey) {
        Optional<UserShippingAddress> exist = addressRepository.findByUserKey(userKey);
        return exist.orElse(null);
    }

    @Transactional
    public boolean createUserAddress(Long userKey, UserShippingAddress address) {
        Optional<UserShippingAddress> exist = addressRepository.findByUserKey(userKey);
        if (exist.isPresent()) {
            return false;
        }
        UserShippingAddress userShippingAddress = UserShippingAddress.of(
                address.getAddressName(),
                address.getAddress(),
                address.getAddressDetail(),
                userKey
        );
        addressRepository.save(userShippingAddress);
        return true;
    }
}
