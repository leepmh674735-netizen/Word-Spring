package com.springinpractice.ch05.mvc;

import com.springinpractice.ch05.domain.Player;
import com.springinpractice.ch05.domain.PlayerSearchCriteria;
import com.springinpractice.ch05.service.PlayerService;

public class FindExistingPlayerAction {
    
    private PlayerService playerService;
    
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService; 
    }
    
    public Player execute(PlayerSearchCriteria criteria) throws Exception {
        if (criteria != null) {
            return playerService.findExistingPlayer(criteria);
        }
        return null;
    }
}
