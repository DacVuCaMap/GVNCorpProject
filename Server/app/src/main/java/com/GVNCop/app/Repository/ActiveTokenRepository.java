package com.GVNCop.app.Repository;

import com.GVNCop.app.Entity.ActiveToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken,Long> {
    @Query("SELECT a FROM ActiveToken a WHERE a.activeTokenName = :token")
    Optional<ActiveToken> findByName(@Param("token")String token);
}
