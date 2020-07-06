package io.alauda;

import io.alauda.config.Config;
import io.alauda.definition.Consumer;
import io.alauda.definition.Producer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

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

        long duration = config.getRun().getDuration() * 60 * 1000;
        System.out.printf("shutdown after %d minutes\n", duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
