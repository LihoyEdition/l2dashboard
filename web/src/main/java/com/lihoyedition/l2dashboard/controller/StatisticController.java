package com.lihoyedition.l2dashboard.controller;

import com.lihoyedition.l2dashboard.model.ServerStats;
import com.lihoyedition.l2dashboard.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */

@RestController
@RequestMapping("/stats")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public Set<ServerStats> getServerStats() {
        return statisticService.getServersStats();
    }

    @RequestMapping(value = "/servers/{id}", method = RequestMethod.GET)
    public ServerStats getServerStats(@PathVariable("id") String id) {
        return statisticService.getServersStats(Integer.parseInt(id));
    }

}
