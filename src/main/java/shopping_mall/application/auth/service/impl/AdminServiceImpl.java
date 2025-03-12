package shopping_mall.application.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_mall.application.auth.service.AuthService;
import shopping_mall.application.auth.service.dto.AuthUser;
import shopping_mall.domain.auth.entity.AdminEntity;
import shopping_mall.domain.auth.enums.ApprovalStatus;
import shopping_mall.domain.auth.enums.Role;
import shopping_mall.domain.auth.exception.login.LoginValidException;
import shopping_mall.domain.auth.exception.register.ValidRoleException;
import shopping_mall.domain.auth.model.Admin;
import shopping_mall.infrastructure.auth.repository.AdminRepository;
import shopping_mall.infrastructure.util.JwtUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AuthService<Admin> {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UniqueIdGenerator uniqueIdGenerator;

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

        if (adminEntity.getStatus() != ApprovalStatus.PENDING ) {
            throw new ValidRoleException();
        }
        // 암호화
        String encodingPassword = passwordEncoder.encode(admin.getPassword());

        // update 될 내용
        adminEntity.updatePassword(adminEntity.getKey(), encodingPassword);
    }

    @Override
    @Transactional
    public String login(Admin admin) {
        // id 체크
        AdminEntity adminEntity = adminRepository.findById(admin.getId())
                .orElseThrow(ValidRoleException::new);

        // role, status 체크
        if (!adminEntity.getStatus().equals(ApprovalStatus.APPROVED) || !adminEntity.getRole().equals(Role.ADMIN)) {
            throw new ValidRoleException();
        }
        if (!passwordEncoder.matches(admin.getPassword(), adminEntity.getPassword())) {
            throw new LoginValidException();
        }
        AuthUser auth = AuthUser.of(adminEntity.getKey(), admin.getId(), adminEntity.getRole());

        return jwtUtil.createAccessToken(auth);
    }

    @Transactional
    public void createAdmin(Admin admin) {
        String uniqueId = uniqueIdGenerator.generateUniqueId();
        AdminEntity entity = admin.toEntity(uniqueId, admin.getName(), admin.getRole());
        adminRepository.save(entity);
    }
}
