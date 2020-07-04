package io.alauda.config;

import lombok.Data;

public @Data
class Sink {
    String to;
    Kafka kafka;
}
