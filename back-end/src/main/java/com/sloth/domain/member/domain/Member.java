package com.sloth.domain.member.domain;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "user_id"})})
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "join_date")
    private Date joinDate;
}
