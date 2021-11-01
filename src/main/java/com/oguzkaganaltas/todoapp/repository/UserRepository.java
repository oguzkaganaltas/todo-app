package com.oguzkaganaltas.todoapp.repository;

import com.oguzkaganaltas.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM USERS WHERE SESSION_ID = ?1",nativeQuery = true)
    User findUserBySessionId(String sessionId);
}
