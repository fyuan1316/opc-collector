package io.alauda.config;

import lombok.Data;

public @Data
class Run {
    private String loglevel;
    private int duration;

    public int getDuration() {
        if (duration < 10) {
            System.err.printf("duration of run settings is too short, use default value (10 minutes)\n");
            return 10;
        }
        return duration;
    }
}
