package com.lihoyedition.l2dashboard.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */

public class OnlineHistory {

    private int serverId;
    private Set<OnlineMeasuredData> history;

    public OnlineHistory(int serverId) {
        this.serverId = serverId;
        history = new LinkedHashSet<>();
    }

    public void add(LocalDate date, int maxOnline, int averageOnline) {
        history.add(new OnlineMeasuredData(date, maxOnline, averageOnline));
    }

    public int getServerId() {
        return serverId;
    }

    public Set<OnlineMeasuredData> getHistory() {
        return history;
    }

    public int getMaxOnline() {
        return history.stream().mapToInt(OnlineMeasuredData::getMaxOnline).max().orElse(0);
    }

    public int getMaxOnline(LocalDate date) {
        Optional<OnlineMeasuredData> optional = history.stream().filter(
                onlineMeasuredData -> onlineMeasuredData.getDate().equals(date)).findAny();
        return optional.isPresent() ? optional.get().getMaxOnline() : 0;
    }

    public int getAverageOnline() {
        return Double.valueOf(
                history.stream().mapToInt(OnlineMeasuredData::getAverageOnline).average().orElse(0)).intValue();
    }

    public int getAverageOnline(LocalDate date) {
        Optional<OnlineMeasuredData> optional = history.stream().filter(
                onlineMeasuredData -> onlineMeasuredData.getDate().equals(date)).findAny();
        return optional.isPresent() ? optional.get().getAverageOnline() : 0;
    }

    private class OnlineMeasuredData {

        private LocalDate date;
        private int maxOnline;
        private int averageOnline;

        OnlineMeasuredData(LocalDate date, int maxOnline, int averageOnline) {
            this.date = date;
            this.maxOnline = maxOnline;
            this.averageOnline = averageOnline;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getMaxOnline() {
            return maxOnline;
        }

        public int getAverageOnline() {
            return averageOnline;
        }
    }
}
