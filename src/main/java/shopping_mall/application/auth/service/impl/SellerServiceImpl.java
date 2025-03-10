package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.service.dto.AuthUser;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.domain.auth.entity.SellerEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.exception.login.LoginValidException;
import shopping_mall.domain.auth.exception.login.NotApproveSellerException;
import shopping_mall.domain.auth.model.Seller;
import shopping_mall.infrastructure.auth.repository.SellerRepository;
import shopping_mall.infrastructure.util.JwtUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerServiceImpl implements AuthService<Seller> {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public boolean checkId(String id) {
        return sellerRepository.findById(id).isPresent();
    }

    @Override
    @Transactional
    public void register(Seller seller) {
        // validation
        seller.validate();
        // 비밀번호 암호화
        String encodingPassword = passwordEncoder.encode(seller.getPassword());
        // 기본 enum 세팅
        Seller createSeller = Seller.of(seller.getId(), seller.getStoreName(), encodingPassword, seller.getRegistrationNumber());
        sellerRepository.save(createSeller.toEntity());


    }

    @Override
    @Transactional
    public String login(Seller seller) {

        // validation
        SellerEntity sellerEntity = sellerRepository.findById(seller.getId())
                .orElseThrow(LoginValidException::new);

        if (!sellerEntity.getRole().equals(Role.SELLER)) {
            throw new LoginValidException();
        }

        else if (sellerEntity.getStatus().equals(ApprovalStatus.PENDING) || sellerEntity.getStatus().equals(ApprovalStatus.REJECTED)) {
            throw new NotApproveSellerException("승인이 되지않은 계정입니다.");
        }

        else if (!passwordEncoder.matches(seller.getPassword(), sellerEntity.getPassword())) {
            throw new LoginValidException();
        }

        // token 생성
        AuthUser authUser = AuthUser.of(sellerEntity.getKey(), sellerEntity.getId(), sellerEntity.getRole());
        return jwtUtil.createAccessToken(authUser);
    }

    public List<SellerEntity> findAll() {
        return sellerRepository.findAllByStatus(ApprovalStatus.PENDING);
    }
}
