package com.springinpractice.ch08.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springinpractice.ch08.domain.UserMessage;

@Repository
public interface UserMessageDao extends JpaRepository<UserMessage, Long> {
    
    default void create(UserMessage userMsg) {
        save(userMsg);
    }
}
