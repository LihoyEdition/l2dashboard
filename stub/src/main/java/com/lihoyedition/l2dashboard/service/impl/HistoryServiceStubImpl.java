package com.lihoyedition.l2dashboard.service.impl;

import com.lihoyedition.l2dashboard.model.OnlineHistory;
import com.lihoyedition.l2dashboard.model.RegistrationHistory;
import com.lihoyedition.l2dashboard.service.HistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Lihoy, 02.05.2016
 */

@Service
public class HistoryServiceStubImpl implements HistoryService {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private Set<OnlineHistory> onlineHistories;
    private RegistrationHistory registrationHistory;

    public HistoryServiceStubImpl() {
        initStubHistory();
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

    private void initStubHistory() {
        onlineHistories = new HashSet<>();
        registrationHistory = new RegistrationHistory();

        OnlineHistory onlineHistory = new OnlineHistory(1);
        for (LocalDate date = LocalDate.of(2013, 1, 1); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            onlineHistory.add(date, RANDOM.nextInt(2000, 3000), RANDOM.nextInt(1000, 2000));
            onlineHistories.add(onlineHistory);
            registrationHistory.add(date, RANDOM.nextInt(100, 200));
        }

        onlineHistory = new OnlineHistory(2);
        for (LocalDate date = LocalDate.of(2014, 1, 1); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            onlineHistory.add(date, RANDOM.nextInt(3000, 3500), RANDOM.nextInt(2500, 3000));
            onlineHistories.add(onlineHistory);
        }

        onlineHistory = new OnlineHistory(3);
        for (LocalDate date = LocalDate.of(2015, 1, 1); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            onlineHistory.add(date, RANDOM.nextInt(5000, 5500), RANDOM.nextInt(4700, 5000));
            onlineHistories.add(onlineHistory);
        }
    }
}
