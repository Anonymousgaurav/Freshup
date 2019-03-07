package com.omninos.freshup.ModelClasses;

public class JoinQueueModel {
    String name;
    String inQueue;

    public JoinQueueModel(String name, String inQueue) {
        this.name = name;
        this.inQueue = inQueue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInQueue() {
        return inQueue;
    }

    public void setInQueue(String inQueue) {
        this.inQueue = inQueue;
    }
}
