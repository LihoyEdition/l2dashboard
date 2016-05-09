package com.lihoyedition.l2dashboard.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Lihoy, 02.05.2016
 */

public class RegistrationHistory {

    private Set<RegistrationMeasuredData> history;

    public RegistrationHistory() {
        history = new LinkedHashSet<>();
    }

    public void add(LocalDate date, int registrationCount) {
        history.add(new RegistrationHistory.RegistrationMeasuredData(date, registrationCount));
    }

    public Set<RegistrationMeasuredData> getHistory() {
        return history;
    }

    private class RegistrationMeasuredData {

        private LocalDate date;
        private int registrationCount;

        public RegistrationMeasuredData(LocalDate date, int registrationCount) {
            this.date = date;
            this.registrationCount = registrationCount;
        }

        public LocalDate getDate() {
            return date;
        }

        public int getRegistrationCount() {
            return registrationCount;
        }
    }
}
