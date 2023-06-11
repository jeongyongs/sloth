package com.sloth.team.domain;

import com.sloth.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User owner;
}
