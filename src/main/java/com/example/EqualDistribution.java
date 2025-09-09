package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class EqualDistribution extends Distribution {

    public EqualDistribution(List<Request> requests, long totalAmountToDistribute) {
        super(requests, totalAmountToDistribute);
    }

    @Override
    public void distribute() {
        List<Request> eligibleRequests = new ArrayList<>();
        for (Request request : requests) {
            if (request.requestedAmount >= request.minLotAmount) {
                eligibleRequests.add(request);
            }
        }

        while (totalAmountToDistribute > 0 && !eligibleRequests.isEmpty()) {
            int countEligibleUser = eligibleRequests.size();

            if (totalAmountToDistribute < countEligibleUser) {
                Random rand = new Random();
                for (int i = 0; i < totalAmountToDistribute; i++) {
                    if (countEligibleUser == 0) break;
                    int randomIndex = rand.nextInt(countEligibleUser);
                    Request selectedRequest = eligibleRequests.get(randomIndex);
                    if (selectedRequest.distributedAmount < selectedRequest.requestedAmount) {
                        selectedRequest.distributedAmount++;
                        totalAmountToDistribute--;

                        if (selectedRequest.distributedAmount >= selectedRequest.requestedAmount) {
                            eligibleRequests.remove(randomIndex);
                            countEligibleUser--;
                        }
                    }
                }
                break;
            }

            Iterator<Request> iterator = eligibleRequests.iterator();
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

        resetRequests();
        redistributeRemainingAmount();
    }
}
