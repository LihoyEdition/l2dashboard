package com.lihoyedition.l2dashboard.controller;

import com.lihoyedition.l2dashboard.model.OnlineHistory;
import com.lihoyedition.l2dashboard.model.RegistrationHistory;
import com.lihoyedition.l2dashboard.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lihoy, 02.05.2016
 */

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public OnlineHistory getOnlineHistory(@RequestParam(value = "serverId") int serverId) {
        return historyService.getOnlineHistory(serverId);
    }

    @RequestMapping(value = "/registrations")
    public RegistrationHistory getRegistrationHistory() {
        return historyService.getRegistrationHistory();
    }

}
