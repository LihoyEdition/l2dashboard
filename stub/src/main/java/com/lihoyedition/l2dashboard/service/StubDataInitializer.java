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
import java.util.Optional;
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

    private static final int TOTAL_MEMORY = 16_384;

    private static Map<Integer, String> servers;

    private Set<OnlineHistory> onlineHistories;
    private RegistrationHistory registrationHistory;

    private Set<ServerInfo> serversInfo;

    public StubDataInitializer() {
        servers = new HashMap<>();
        servers.put(1, "x1");
        servers.put(2, "x10");
        servers.put(3, "x50");
        servers.put(4, "x1500");

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

        for (Map.Entry<Integer, String> entry : servers.entrySet()) {
            OnlineHistory onlineHistory = new OnlineHistory(entry.getKey());

            for (LocalDate date = LocalDate.of(2013 + getRandom().nextInt(0, 3), 1, 1); date.isBefore(
                    LocalDate.now()); date = date.plusDays(1)) {
                int base = entry.getKey() * 1000;
                int maxOnline = getRandom().nextInt(base, (int) (1.25 * base));
                int avgOnline = getRandom().nextInt((int) (0.75 * base), base);
                onlineHistory.add(date, maxOnline, avgOnline);
                onlineHistories.add(onlineHistory);
            }
        }

        registrationHistory = new RegistrationHistory();
        for (LocalDate date = LocalDate.of(2013, 1, 1); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
            registrationHistory.add(date, getRandom().nextInt(100, 200));
        }
    }

    private ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    private void initStubServersInfo() {
        serversInfo = new LinkedHashSet<>();

        for (Map.Entry<Integer, String> entry : servers.entrySet()) {
            if (getRandom().nextBoolean()) {
                serversInfo.add(new ServerInfo(entry.getKey(), entry.getValue(), Status.UP, getCurrentOnline(),
                                               buildStubThreadPoolExecutors(), TOTAL_MEMORY, getFreeMemory(),
                                               getUptime()));
            } else {
                serversInfo.add(new ServerInfo(entry.getKey(), entry.getValue(), Status.DOWN, 0, null, 0, 0, 0));
            }
        }
    }

    public void updateStubServerInfo(int id, boolean isDown) {
        Optional<ServerInfo> serverInfoOptional = serversInfo.stream().filter(info -> info.getId() == id).findAny();
        if (serverInfoOptional.isPresent()) {
            ServerInfo serverInfo = serverInfoOptional.get();
            if (!isDown) {
                serverInfo.setStatus(Status.UP);
                serverInfo.setCurrentOnline(getCurrentOnline());
                serverInfo.setFreeMemory(getFreeMemory());
                serverInfo.setUptime(getUptime());
            } else {
                serverInfo.setStatus(Status.DOWN);
                serverInfo.setCurrentOnline(0);
                serverInfo.setFreeMemory(0);
                serverInfo.setUptime(0);
            }
        }
    }

    private int getCurrentOnline() {
        return getRandom().nextInt(1000, 3000);
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

    private int getFreeMemory() {
        return getRandom().nextInt(TOTAL_MEMORY);
    }

    private long getUptime() {
        return getRandom().nextLong(86_400_000, 604_800_000);
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
            return getRandom().nextInt(corePoolSize, maximumPoolSize);
        }

        @Override
        public int getActiveCount() {
            return getRandom().nextInt(corePoolSize / 2);
        }

        @Override
        public int getLargestPoolSize() {
            return getRandom().nextInt(maximumPoolSize);
        }

        @Override
        public long getCompletedTaskCount() {
            return getRandom().nextInt(100, 200);
        }

        @Override
        public BlockingQueue<Runnable> getQueue() {
            return super.getQueue();
        }
    }
}
