package com.GVNCop.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activeToken")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accId")
    private Account accId;
    @Column(name = "activeTokenName")
    private String activeTokenName;
    @Column(name = "revoked")
    private boolean revoked;
    @Column(name = "expiration")
    private boolean expiration;
    @Column(name = "domain")
    private String domain;
    @Column(name = "todo")
    private String todo;
}
