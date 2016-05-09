package com.lihoyedition.l2dashboard.service;

import com.lihoyedition.l2dashboard.model.OnlineHistory;
import com.lihoyedition.l2dashboard.model.RegistrationHistory;

/**
 * @author Lihoy, 02.05.2016
 */
public interface HistoryService {

    OnlineHistory getOnlineHistory(int serverId);

    RegistrationHistory getRegistrationHistory();

}
