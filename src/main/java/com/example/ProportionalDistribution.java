package com.example;

import java.util.ArrayList;
import java.util.List;

class ProportionalDistribution extends Distribution {

    public ProportionalDistribution(List<Request> requests, long totalAmountToDistribute) {
        super(requests, totalAmountToDistribute);
    }

    @Override
    public void distribute() {
        long totalRequested = 0;
        List<Request> eligibleRequests = new ArrayList<>();

        for (Request request : requests) {
            if (request.requestedAmount >= request.minLotAmount) {
                totalRequested += request.requestedAmount;
                eligibleRequests.add(request);
            }
        }

        if (totalRequested == 0) return;

        for (Request request : eligibleRequests) {
            long proportion = (long) request.requestedAmount / totalRequested;
            request.distributedAmount = (long) Math.floor(proportion * totalAmountToDistribute);
        }

        long remainingAmount = totalAmountToDistribute - eligibleRequests.stream().mapToLong(r -> r.distributedAmount).sum();
        while (remainingAmount > 0) {
            for (Request request : eligibleRequests) {
                if (remainingAmount > 0 && request.distributedAmount < request.requestedAmount) {
                    request.distributedAmount++;
                    remainingAmount--;
                }
            }
        }

        resetRequests();
        redistributeRemainingAmount();
    }
}
