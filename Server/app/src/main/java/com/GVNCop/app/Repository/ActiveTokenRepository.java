package com.GVNCop.app.Repository;

import com.GVNCop.app.Entity.ActiveToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken,Long> {
    @Query("SELECT a FROM ActiveToken a WHERE a.activeTokenName = :token")
    Optional<ActiveToken> findByName(@Param("token")String token);

    @Query("SELECT a FROM ActiveToken a WHERE a.account.id = :accId AND a.todo = :todo")
    Optional<ActiveToken> findByAccAndTodo(@Param("accId") Long accId, @Param("todo") String todo);
}
