package com.sloth.domain.handover.domain;

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
public class Handover {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "transferor_id")
    private User transferor;
    private String title;
    private String content;
    @Column(name = "create_date")
    private Date createDate;
    private boolean state;
}
