package shopping_mall.application.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.service.AuthService;
import shopping_mall.domain.model.Seller;
import shopping_mall.infrastructure.repository.SellerRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerServiceImpl implements AuthService<Seller> {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

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
        return "";
    }
}
