package com.murabi.murker;

import java.util.ArrayList;

import com.murabi.murker.interchange.GameResponse;
import com.murabi.murker.interchange.StateResponse;
import com.murabi.conf.Config;

public class Scheduler {
    private Config config;
    private Client client;

    public Scheduler(Config config, Client client) {
        this.config = config;
        this.client = client;
    }

    public String electMurker() throws java.io.IOException {
        int minGameCount = Integer.MAX_VALUE;
        int leastLoadedIdx = 0;
        
        for (int i = 0; i < this.config.murkerAddresses.size(); i++) {
            String addr = this.config.murkerAddresses.get(i);
            StateResponse resp = client.fetchState(addr);

            if (resp.gameCount < minGameCount) {
                minGameCount = resp.gameCount;
                leastLoadedIdx = i;
            }
        }

        return this.config.murkerAddresses.get(leastLoadedIdx);
    }
}
