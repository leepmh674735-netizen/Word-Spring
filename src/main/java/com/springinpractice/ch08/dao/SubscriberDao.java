package com.springinpractice.ch08.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springinpractice.ch08.domain.Subscriber;

@Repository
public interface SubscriberDao extends JpaRepository<Subscriber, Long> {
    
    default Subscriber load(Long id) {
        return findById(id).orElse(null);
    }
    
    default void create(Subscriber subscriber) {
        save(subscriber);
    }
    
    default void update(Subscriber subscriber) {
        save(subscriber);
    }
}
