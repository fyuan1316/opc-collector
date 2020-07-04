package io.alauda;

import io.alauda.config.Config;
import io.alauda.consumer.KafkaConsumer;
import io.alauda.definition.Consumer;
import io.alauda.definition.Producer;
import io.alauda.producer.local.LocalCSVProducer;
import io.alauda.producer.remote.CSVProducer;

public class Factory {


    public static Consumer CreateConsumer(Config config) {

        return new KafkaConsumer(config);
    }

    public static Producer CreateProducer(Config config) {
        if ("remote".equalsIgnoreCase(config.getSource().getFrom())) {
            return new CSVProducer(config);
        } else {
            return new LocalCSVProducer(config);
        }
    }

}
