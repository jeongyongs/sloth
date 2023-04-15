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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "member_id"})})
public class InviteTeam {

    @Id
    @GeneratedValue
    @Column(name = "invite_team_id")
    private Long id;
    private Long expired;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
