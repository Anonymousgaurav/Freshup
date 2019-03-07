package com.omninos.freshup.ModelClasses;

public class SingleUserQueueList {
    String name;
    String serviceType;

    public SingleUserQueueList(String name, String serviceType) {
        this.name = name;
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
