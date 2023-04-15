package com.sloth.team.domain;

import com.sloth.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamMember {

    @Id
    @GeneratedValue
    @Column(name = "team_member_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
