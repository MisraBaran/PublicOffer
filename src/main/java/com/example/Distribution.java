package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class Distribution {
    List<Request> requests;
    long totalAmountToDistribute;

    public Distribution(List<Request> requests, long totalAmountToDistribute) {
        this.requests = requests;
        this.totalAmountToDistribute = totalAmountToDistribute;
    }

    public abstract void distribute();

    public void printDistribution() {
        for (Request request : requests) {
            System.out.println(request.tc + " " + request.username + " " + request.surname + " - Dağıtılan: " + request.distributedAmount);
        }
    }

    protected void resetRequests() {
        for (Request request : requests) {
            if (request.distributedAmount < request.minLotAmount) {
                totalAmountToDistribute += request.distributedAmount;
                request.distributedAmount = 0L;
            }
        }
    }

    protected void redistributeRemainingAmount() {
        List<Request> finalEligibleRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request.distributedAmount >= request.minLotAmount) {
                finalEligibleRequests.add(request);
            }
        }

        while (totalAmountToDistribute > 0 && !finalEligibleRequests.isEmpty()) {
            Iterator<Request> iterator = finalEligibleRequests.iterator();
            while (iterator.hasNext()) {
                Request request = iterator.next();
                if (totalAmountToDistribute > 0 && request.distributedAmount < request.requestedAmount) {
                    request.distributedAmount++;
                    totalAmountToDistribute--;
                    if (request.distributedAmount >= request.requestedAmount) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
