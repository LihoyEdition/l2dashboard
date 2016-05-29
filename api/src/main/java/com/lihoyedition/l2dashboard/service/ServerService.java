package com.lihoyedition.l2dashboard.service;

import com.lihoyedition.l2dashboard.model.ServerInfo;

import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */
public interface ServerService {

    Set<ServerInfo> getServersInfo();

    ServerInfo getServerInfo(int id);

    void start(int id);

    void shutdown(int id);

    void restart(int id);

}
