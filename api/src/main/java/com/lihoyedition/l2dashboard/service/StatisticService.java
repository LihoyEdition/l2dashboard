package com.lihoyedition.l2dashboard.service;

import com.lihoyedition.l2dashboard.model.ServerStats;

import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */
public interface StatisticService {

    Set<ServerStats> getServersStats();

    ServerStats getServersStats(int id);

}
