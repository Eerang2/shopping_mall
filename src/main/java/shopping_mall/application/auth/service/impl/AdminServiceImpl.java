package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.domain.auth.entity.AdminEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.exception.register.ValidRoleException;
import shopping_mall.domain.auth.model.Admin;
import shopping_mall.infrastructure.auth.repository.AdminRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AuthService<Admin> {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkId(String id) {
        // 최고 관리자가 생성한 아이디와 일치하는지 검사
        return adminRepository.existsById(id);
    }

    @Override
    @Transactional
    public void register(Admin admin) {
        // id 체크
        AdminEntity adminEntity = adminRepository.findById(admin.getId())
                        .orElseThrow(ValidRoleException::new);

        if (adminEntity.getStatus() != ApprovalStatus.PENDING || adminEntity.getPassword() == null) {
            throw new ValidRoleException();
        }
        // 암호화
        String encodingPassword = passwordEncoder.encode(admin.getPassword());
        // 기본 enum 세팅
        Admin createAdmin = Admin.of(adminEntity.getKey(), admin.getId(), encodingPassword);
        // 저장
        adminRepository.save(createAdmin.toEntity());
    }

    @Override
    @Transactional
    public String login(Admin user) {
        // id 체크
        // role 체크
        return "";
    }
}
