package com.lihoyedition.l2dashboard.service.impl;

import com.lihoyedition.l2dashboard.model.ServerInfo;
import com.lihoyedition.l2dashboard.service.ServerService;
import com.lihoyedition.l2dashboard.service.StubDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */
@Service
public class ServerServiceStubImpl implements ServerService {

    @Autowired
    private StubDataInitializer stubDataInitializer;

    private Set<ServerInfo> serversInfo;

    @PostConstruct
    public void init() {
        serversInfo = stubDataInitializer.getServersInfo();
    }

    @Override
    public Set<ServerInfo> getServersInfo() {
        return serversInfo;
    }

    @Override
    public ServerInfo getServerInfo(int id) {
        return serversInfo.stream().filter(info -> info.getId() == id).findAny().orElse(null);
    }

    @Override
    public void start(int id) {
        // Imagine that server will be started :)
        stubDataInitializer.updateStubServerInfo(id, false);
    }

    @Override
    public void shutdown(int id) {
        // Imagine that server will be stopped :)
        stubDataInitializer.updateStubServerInfo(id, true);
    }

    @Override
    public void restart(int id) {
        // Imagine that server will be restarted :)
        stubDataInitializer.updateStubServerInfo(id, false);
    }

}
