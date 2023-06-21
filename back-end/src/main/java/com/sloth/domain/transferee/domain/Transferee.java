package com.sloth.domain.transferee.domain;

import com.sloth.domain.handover.domain.Handover;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"handover_id", "user_id"})})
public class Transferee {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "handover_id")
    private Handover handover;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "transfer_date")
    private Date transferDate;
    private boolean state;
}
