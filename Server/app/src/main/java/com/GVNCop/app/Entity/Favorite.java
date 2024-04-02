package com.GVNCop.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "Favorite")
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accId")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catId")
    private Category category;
    @Column(name = "score")
    private int score;
}
