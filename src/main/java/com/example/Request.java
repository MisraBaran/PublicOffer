package com.example;

public class Request {
    String tc;
    String username;
    String surname;
    String organizationName;
    Long requestedAmount;
    Long minLotAmount;
    Long distributedAmount;
    Boolean duplicateRecord;

    public Request(String tc, String username, String surname, String organizationName, Long requestedAmount, Long minLotAmount, Long distributedAmount, Boolean duplicateRecord ) {
        this.tc = tc;
        this.username = username;
        this.surname = surname;
        this.requestedAmount = requestedAmount;
        this.minLotAmount = minLotAmount; 
        this.distributedAmount = distributedAmount;
        this.duplicateRecord = duplicateRecord; 
    }

    public long getDistributedAmount() {
        return this.distributedAmount;
    }

    public String getTc() {
        return this.tc;
    }
} 
    