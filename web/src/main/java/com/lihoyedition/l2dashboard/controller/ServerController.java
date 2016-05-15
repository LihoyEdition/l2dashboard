package com.lihoyedition.l2dashboard.controller;

import com.lihoyedition.l2dashboard.model.ServerInfo;
import com.lihoyedition.l2dashboard.service.ServerService;
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
@RequestMapping("/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @RequestMapping(method = RequestMethod.GET)
    public Set<ServerInfo> getServersInfo() {
        return serverService.getServersInfo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServerInfo getServerInfo(@PathVariable("id") String id) {
        return serverService.getServerInfo(Integer.parseInt(id));
    }

}
