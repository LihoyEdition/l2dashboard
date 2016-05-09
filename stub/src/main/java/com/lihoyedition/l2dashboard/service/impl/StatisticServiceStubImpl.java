package com.lihoyedition.l2dashboard.service.impl;

import com.lihoyedition.l2dashboard.model.ServerStats;
import com.lihoyedition.l2dashboard.model.Status;
import com.lihoyedition.l2dashboard.service.StatisticService;
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
public class StatisticServiceStubImpl implements StatisticService {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private Set<ServerStats> serverStats;

    public StatisticServiceStubImpl() {
        initStubStats();
    }

    @Override
    public Set<ServerStats> getServersStats() {
        return serverStats;
    }

    @Override
    public ServerStats getServersStats(int id) {
        return serverStats.stream().filter(stats -> stats.getId() == id).findAny().orElse(null);
    }

    private void initStubStats() {
        serverStats = new LinkedHashSet<>();
        serverStats.add(new ServerStats(1, "x1", Status.UP, RANDOM.nextInt(1000, 2000), buildStubThreadPoolExecutors(),
                                        16_777_216, RANDOM.nextInt(16_777_216)));
        serverStats.add(new ServerStats(2, "x50", Status.UP, RANDOM.nextInt(1000, 2000), buildStubThreadPoolExecutors(),
                                        33_554_432, RANDOM.nextInt(33_554_432)));
        serverStats.add(
                new ServerStats(3, "x100", Status.UP, RANDOM.nextInt(1000, 2000), buildStubThreadPoolExecutors(),
                                67_108_864, RANDOM.nextInt(67_108_864)));
        serverStats.add(new ServerStats(4, "x1500", Status.DOWN, 0, null, 0, 0));
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
