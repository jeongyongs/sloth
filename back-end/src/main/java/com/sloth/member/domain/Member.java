package com.sloth.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity                         // spring bean 관리
@Data                           // getter, setter 설정
@AllArgsConstructor             // 모든 필드 생성자
@NoArgsConstructor              // 빈 생성자
@Builder                        // 빌드 패턴 적용
public class Member {

    @Id                         // PK
    @GeneratedValue             // 시퀀스 자동 번호
    @Column(name = "member_id")
    private Long id;            // 고유번호
    @Column(unique = true, nullable = false)
    private String username;    // 아이디
    @Column(nullable = false)
    private String password;    // 비밀번호
    private String name;        // 이름
    private String email;       // 이메일
    private String phone;       // 전화번호
}
