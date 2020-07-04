package io.alauda.config;

import lombok.Data;

public @Data
class Kafka {
    String brokerlist;
    String topic;
    String msgspliter;
}
