package io.alauda;

import io.alauda.config.Config;
import io.alauda.config.Local;
import io.alauda.consumer.KafkaConsumer;
import io.alauda.definition.Consumer;
import io.alauda.definition.Producer;
import io.alauda.definition.Product;
import io.alauda.producer.local.LocalCSVProducer;
import io.alauda.product.MetricData;

import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) {

        Config config = new Option().parse(args);

        Consumer<MetricData> consumer = Factory.CreateConsumer(config);
        Producer<MetricData> producer = Factory.CreateProducer(config);

        BlockingQueue<Product<MetricData>> queue = new SynchronousQueue<Product<MetricData>>();
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(new io.alauda.producer.Worker<Long>(producer, queue));
//        executorService.execute(new io.alauda.consumer.Worker<Long>(consumer, queue));
        executorService.execute(new io.alauda.producer.Worker<MetricData>(producer, queue));
        executorService.execute(new io.alauda.consumer.Worker<MetricData>(consumer, queue));
        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}