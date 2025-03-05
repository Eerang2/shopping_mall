package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.domain.auth.model.Admin;
import shopping_mall.infrastructure.auth.repository.AdminRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AuthService<Admin> {

    private final AdminRepository adminRepository;

    @Override
    public boolean checkId(String id) {
        // 최고 관리자가 생성한 아이디와 일치하는지 검사
        return adminRepository.existsById(id);
    }

    @Override
    public void register(Admin user) {
        // id 체크
        //

    }

    @Override
    public String login(Admin user) {
        return "";
    }
}
