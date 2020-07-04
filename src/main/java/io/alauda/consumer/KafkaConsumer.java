package io.alauda.consumer;

import io.alauda.config.Config;
import io.alauda.definition.Consumer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;

public class KafkaConsumer implements Consumer<MetricData> {
    private Config config;

    public KafkaConsumer(Config config) {
        this.config = config;
    }

    @Override
    public void consume(Product<MetricData> product) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        System.out.printf("kafka writer will write data: %s\n", product.get());
    }
}
