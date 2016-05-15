package com.lihoyedition.l2dashboard.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Lihoy, 02.05.2016
 */
public class ServerInfo {

    private int id;
    private String name;
    private Status status;
    private int currentOnline;
    private Set<ThreadStats> threadStats;
    private long memory;
    private long freeMemory;
    private MemoryUnit memoryUnit;

    public ServerInfo(int id, String name, Status status, int currentOnline,
                      Map<String, ThreadPoolExecutor> threadPoolExecutors, long memory, long freeMemory,
                      MemoryUnit memoryUnit) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.currentOnline = currentOnline;
        this.threadStats = buildThreadStats(threadPoolExecutors);
        this.memory = memory;
        this.freeMemory = freeMemory;
        this.memoryUnit = memoryUnit;
    }

    public ServerInfo(int id, String name, Status status, int currentOnline,
                      Map<String, ThreadPoolExecutor> threadPoolExecutors, long memory, long freeMemory) {
        this(id, name, status, currentOnline, threadPoolExecutors, memory, freeMemory, MemoryUnit.MB);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrentOnline() {
        return currentOnline;
    }

    public void setCurrentOnline(int currentOnline) {
        this.currentOnline = currentOnline;
    }

    public Set<ThreadStats> getThreadStats() {
        return threadStats;
    }

    public void setThreadStats(Set<ThreadStats> threadStats) {
        this.threadStats = threadStats;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public MemoryUnit getMemoryUnit() {
        return memoryUnit;
    }

    public void setMemoryUnit(MemoryUnit memoryUnit) {
        this.memoryUnit = memoryUnit;
    }

    private Set<ThreadStats> buildThreadStats(Map<String, ThreadPoolExecutor> threadPoolExecutors) {
        if (threadPoolExecutors == null || threadPoolExecutors.isEmpty()) {
            return null;
        }

        threadStats = new HashSet<>();
        for (Map.Entry<String, ThreadPoolExecutor> entry : threadPoolExecutors.entrySet()) {
            ThreadPoolExecutor threadPoolExecutor = entry.getValue();
            threadStats.add(new ThreadStats(entry.getKey(), threadPoolExecutor.getActiveCount(),
                                            threadPoolExecutor.getCorePoolSize(),
                                            threadPoolExecutor.getMaximumPoolSize(),
                                            threadPoolExecutor.getLargestPoolSize(), threadPoolExecutor.getPoolSize(),
                                            threadPoolExecutor.getCompletedTaskCount(),
                                            threadPoolExecutor.getQueue().size()));
        }
        return threadStats;
    }

    private class ThreadStats {
        private String poolName;
        private int activeCount;
        private int corePoolSize;
        private int maximumPoolSize;
        private int largestPoolSize;
        private int poolSize;
        private long completedTaskCount;
        private int queueSize;

        ThreadStats(String poolName, int activeCount, int corePoolSize, int maximumPoolSize, int largestPoolSize,
                    int poolSize, long completedTaskCount, int queueSize) {
            this.poolName = poolName;
            this.activeCount = activeCount;
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.largestPoolSize = largestPoolSize;
            this.poolSize = poolSize;
            this.completedTaskCount = completedTaskCount;
            this.queueSize = queueSize;
        }

        public String getPoolName() {
            return poolName;
        }

        public int getActiveCount() {
            return activeCount;
        }

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public int getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public int getLargestPoolSize() {
            return largestPoolSize;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public long getCompletedTaskCount() {
            return completedTaskCount;
        }

        public int getQueueSize() {
            return queueSize;
        }
    }
}
