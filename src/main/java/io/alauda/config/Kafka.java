package io.alauda.config;

import lombok.Data;

public @Data
class Kafka {
    String brokerlist;
    String topic;
    String msgspliter;
    String sasl_enable;
    String sasl_username;
    String sasl_passwd;
    String protocol;
    String sasl_mechanism;
    String key_serializer;
    String value_serializer;

}
