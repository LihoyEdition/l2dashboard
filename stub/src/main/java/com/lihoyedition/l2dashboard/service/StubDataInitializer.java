package com.lihoyedition.l2dashboard.service;

import com.lihoyedition.l2dashboard.model.OnlineHistory;
import com.lihoyedition.l2dashboard.model.RegistrationHistory;
import com.lihoyedition.l2dashboard.model.ServerInfo;
import com.lihoyedition.l2dashboard.model.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lihoy, 19.05.2016
 */
@Service
public class StubDataInitializer {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private Set<OnlineHistory> onlineHistories;
    private RegistrationHistory registrationHistory;

    private Set<ServerInfo> serversInfo;

    public StubDataInitializer() {
        initStubHistory();
        initStubServersInfo();
    }

    public Set<OnlineHistory> getOnlineHistories() {
        return onlineHistories;
    }

    public RegistrationHistory getRegistrationHistory() {
        return registrationHistory;
    }

    public Set<ServerInfo> getServersInfo() {
        return serversInfo;
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

        onlineHistory = new OnlineHistory(4);
        for (LocalDate date = LocalDate.of(2016, 1, 1); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            onlineHistory.add(date, RANDOM.nextInt(800, 1000), RANDOM.nextInt(500, 800));
            onlineHistories.add(onlineHistory);
        }
    }

    private void initStubServersInfo() {
        serversInfo = new LinkedHashSet<>();
        serversInfo.add(new ServerInfo(1, "x1", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 16_384,
                                       getFreeMemory(16_384), getUptime()));
        serversInfo.add(new ServerInfo(2, "x50", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 32_768,
                                       getFreeMemory(32_768), getUptime()));
        serversInfo.add(new ServerInfo(3, "x100", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 65_536,
                                       getFreeMemory(65_536), getUptime()));
        serversInfo.add(new ServerInfo(4, "x1500", Status.DOWN, 0, null, 0, 0, 0));
    }

    private int getCurrentOnline() {
        return RANDOM.nextInt(1000, 3000);
    }

    private Map<String, ThreadPoolExecutor> buildStubThreadPoolExecutors() {
        Map<String, ThreadPoolExecutor> threadPoolExecutors = new HashMap<>();
        threadPoolExecutors.put("General Pool",
                                new ThreadPoolExecutorStub(10, 20, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
        threadPoolExecutors.put("I/O Packet Pool",
                                new ThreadPoolExecutorStub(20, 40, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
        threadPoolExecutors.put("AI Pool",
                                new ThreadPoolExecutorStub(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));

        return threadPoolExecutors;
    }

    private int getFreeMemory(int bound) {
        return RANDOM.nextInt(bound);
    }

    private long getUptime() {
        return RANDOM.nextLong(86_400_000, 604_800_000);
    }

    private class ThreadPoolExecutorStub extends ThreadPoolExecutor {

        private int corePoolSize;
        private int maximumPoolSize;

        ThreadPoolExecutorStub(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
        }

        @Override
        public int getPoolSize() {
            return RANDOM.nextInt(corePoolSize, maximumPoolSize);
        }

        @Override
        public int getActiveCount() {
            return RANDOM.nextInt(corePoolSize / 2);
        }

        @Override
        public int getLargestPoolSize() {
            return RANDOM.nextInt(maximumPoolSize);
        }

        @Override
        public long getCompletedTaskCount() {
            return RANDOM.nextInt(100, 200);
        }

        @Override
        public BlockingQueue<Runnable> getQueue() {
            return super.getQueue();
        }
    }
}
