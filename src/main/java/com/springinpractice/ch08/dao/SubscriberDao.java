package com.springinpractice.ch08.dao;

import com.springinpractice.ch08.domain.Subscriber;

public interface SubscriberDao {
    Subscriber load(Long id);
    void create(Subscriber subscriber);
    void update(Subscriber subscriber);
}
