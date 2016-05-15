package com.lihoyedition.l2dashboard.service.impl;

import com.lihoyedition.l2dashboard.model.ServerInfo;
import com.lihoyedition.l2dashboard.model.Status;
import com.lihoyedition.l2dashboard.service.ServerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lihoy, 02.05.2016
 */
@Service
public class ServerServiceStubImpl implements ServerService {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private Set<ServerInfo> serversInfo;

    public ServerServiceStubImpl() {
        initStubInfo();
    }

    @Override
    public Set<ServerInfo> getServersInfo() {
        return serversInfo;
    }

    @Override
    public ServerInfo getServerInfo(int id) {
        return serversInfo.stream().filter(info -> info.getId() == id).findAny().orElse(null);
    }

    private void initStubInfo() {
        serversInfo = new LinkedHashSet<>();
        serversInfo.add(new ServerInfo(1, "x1", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 16_384,
                                       getFreeMemory(16_384)));
        serversInfo.add(new ServerInfo(2, "x50", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 32_768,
                                       getFreeMemory(32_768)));
        serversInfo.add(new ServerInfo(3, "x100", Status.UP, getCurrentOnline(), buildStubThreadPoolExecutors(), 65_536,
                                       getFreeMemory(65_536)));
        serversInfo.add(new ServerInfo(4, "x1500", Status.DOWN, 0, null, 0, 0));
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
