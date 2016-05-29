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
    public ServerInfo getServerInfo(@PathVariable("id") int id) {
        return serverService.getServerInfo(id);
    }

    @RequestMapping(value = "/{id}/start", method = RequestMethod.POST)
    public void startServer(@PathVariable("id") int id) {
        serverService.start(id);
    }

    @RequestMapping(value = "/{id}/shutdown", method = RequestMethod.POST)
    public void shutdownServer(@PathVariable("id") int id) {
        serverService.shutdown(id);
    }

    @RequestMapping(value = "/{id}/restart", method = RequestMethod.POST)
    public void restartServer(@PathVariable("id") int id) {
        serverService.restart(id);
    }

}
