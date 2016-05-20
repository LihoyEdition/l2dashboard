package com.lihoyedition.l2dashboard.service.impl;

import com.lihoyedition.l2dashboard.model.OnlineHistory;
import com.lihoyedition.l2dashboard.model.RegistrationHistory;
import com.lihoyedition.l2dashboard.service.HistoryService;
import com.lihoyedition.l2dashboard.service.StubDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */
@Service
public class HistoryServiceStubImpl implements HistoryService {

    @Autowired
    private StubDataInitializer stubDataInitializer;

    private Set<OnlineHistory> onlineHistories;
    private RegistrationHistory registrationHistory;

    @PostConstruct
    public void init() {
        onlineHistories = stubDataInitializer.getOnlineHistories();
        registrationHistory = stubDataInitializer.getRegistrationHistory();
    }

    @Override
    public OnlineHistory getOnlineHistory(int serverId) {
        return onlineHistories.stream().filter(
                onlineHistory -> onlineHistory.getServerId() == serverId).findAny().orElse(null);
    }

    @Override
    public RegistrationHistory getRegistrationHistory() {
        return registrationHistory;
    }

}
