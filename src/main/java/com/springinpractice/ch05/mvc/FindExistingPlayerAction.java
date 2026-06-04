package com.springinpractice.ch05.mvc;

import javax.swing.Action;
import java.awt.Event;
import org.apache.tomcat.util.http.fileupload.RequestContext;
public class FindExistingPlayerAction implements Action {
    
    private PlayerService playerService;
    
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService; 
    }
    
    @Override
    public Event execute(RequestContext context) throws Exception {
        PlayerSearchCriteria criteria = 
                (PlayerSearchCriteria) context.getFlowScope().get("playerSearchCriteria");
                 
        if (criteria != null) {
            Player player = playerService.findExistingPlayer(criteria);
            context.getFlowScope().put("player", player);
             
            return new Event(this, "success");                 
        } else {
            return new Event(this, "error");
        }
    }
}