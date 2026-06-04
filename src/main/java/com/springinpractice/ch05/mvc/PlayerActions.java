package com.springinpractice.ch05.mvc;

import java.awt.Event;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.webflow.action.MultiAction;

public class PlayerActions extends MultiAction {
    
    private PlayerService playerService;

    // 의존성 주입(DI)을 위한 Setter
    public void setPlayerService(PlayerService playerService) {
        ;
    }
    
    // 비즈니스 로직 메서드
    public Event findExistingPlayer(RequestContext context) {
        // 1. flowScope에서 검색 조건(Criteria) 가져오기
        PlayerSearchCriteria criteria = 
            (PlayerSearchCriteria) context.getFlowScope().get("playerSearchCriteria");
            
        if (criteria != null) {
            // 2. 서비스 레이어를 통해 플레이어 검색 (파라미터는 context가 아니라 주입받은 service를 사용해야 합니다)
            Player player = playerService.findExistingPlayer(criteria);
            
            // 3. 검색 결과를 flowScope에 저장
            context.getFlowScope().put("player", player);
            
            // 4. 성공 이벤트 반환 (MultiAction의 내장 메서드)
            return success();
        } else {
            // 5. 실패/에러 이벤트 반환
            return error();
        }
    }
    
    public Event doSomethingElse(RequestContext context) {
        // 추가 로직 구현 후 Event 반환 필요
        return success();
    }
}