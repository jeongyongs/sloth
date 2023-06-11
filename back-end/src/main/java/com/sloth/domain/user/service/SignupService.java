package com.sloth.domain.user.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.dto.SignupDto;
import com.sloth.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void signup(SignupDto data) throws Exception {  // 회원가입 서비스

        String usernameRegex = "^[a-zA-Z0-9_.]{4,20}$";

        if (data.getUsername().matches(usernameRegex)) {    // 아이디 확인
            User user = User.builder()
                    .username(data.getUsername())
                    .password(encoder.encode(data.getPassword()))   // 비밀번호 암호화
                    .name(data.getName())
                    .build();
            userRepository.save(user);
            return;
        }
        throw new Exception("사용할 수 없는 아이디");
    }
}
