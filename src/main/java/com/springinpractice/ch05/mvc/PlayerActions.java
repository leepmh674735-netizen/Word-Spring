package com.springinpractice.ch05.mvc;

import com.springinpractice.ch05.domain.Player;
import com.springinpractice.ch05.domain.PlayerSearchCriteria;
import com.springinpractice.ch05.service.PlayerService;

public class PlayerActions {
    
    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    public Player findExistingPlayer(PlayerSearchCriteria criteria) {
        if (criteria != null) {
            return playerService.findExistingPlayer(criteria);
        }
        return null;
    }
    
    public String doSomethingElse() {
        return "success";
    }
}