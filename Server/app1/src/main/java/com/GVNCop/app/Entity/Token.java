package com.GVNCop.app.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "tokenString")
    private String tokenString;
    @Column(name = "expiration")
    private boolean expiration;
    @Column(name = "revoked")
    private boolean revoked;
}
