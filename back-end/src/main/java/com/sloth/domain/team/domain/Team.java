package com.sloth.domain.team.domain;

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
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User leader;
    @Column(name = "create_date")
    private Date createDate;
}
