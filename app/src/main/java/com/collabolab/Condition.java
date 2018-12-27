package com.collabolab;

import java.util.ArrayList;

public class Condition {

    String capacity;
    ArrayList<String> itemList;
    String startDate;
    String endDate;

    private Condition() {
    }

    private static class LazyHolder {
        public static final Condition INSTANCE = new Condition();
    }

    public static Condition getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
