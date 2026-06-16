package com.springinpractice.ch05.service;

import com.springinpractice.ch05.domain.Player;
import com.springinpractice.ch05.domain.PlayerSearchCriteria;

public interface PlayerService {
    Player findExistingPlayer(PlayerSearchCriteria criteria);
}
