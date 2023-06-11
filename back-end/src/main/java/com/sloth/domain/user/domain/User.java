package com.sloth.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                         // spring bean 관리
@Data                           // getter, setter 설정
@AllArgsConstructor             // 모든 필드 생성자
@NoArgsConstructor              // 빈 생성자
@Builder                        // 빌드 패턴 적용
public class User {

    @Id                         // PK
    @GeneratedValue             // 시퀀스 자동 번호
    private Long id;            // 고유번호
    @Column(unique = true, nullable = false)
    private String username;    // 아이디
    @Column(nullable = false)
    private String password;    // 비밀번호
    @Column(nullable = false)
    private String name;        // 이름
}
