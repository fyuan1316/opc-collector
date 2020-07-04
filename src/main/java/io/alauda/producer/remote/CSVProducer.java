package io.alauda.producer.remote;

import io.alauda.config.Config;
import io.alauda.definition.Producer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;

public class CSVProducer implements Producer<MetricData> {
    private Config config;

    public CSVProducer(Config config) {
        this.config = config;
    }

    public void init() {

    }

    @Override
    public Product<MetricData> produce() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        return new Product<MetricData>() {
            @Override
            public MetricData get() {
                long t = System.currentTimeMillis();
                System.out.printf("produce:%d\n", t);

                return null;
            }
        };
    }
}
