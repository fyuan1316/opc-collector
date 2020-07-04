package io.alauda.consumer;

import io.alauda.definition.Consumer;
import io.alauda.definition.Product;

import java.util.concurrent.BlockingQueue;

public class Worker<E> implements Consumer<E>, Runnable {
    BlockingQueue<Product<E>> queue;
    Consumer<E> consumer;
    volatile boolean stop = false;

    @Override
    public void consume(Product<E> product) {
        this.consumer.consume(product);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Product<E> product = this.queue.take();
                this.consume(product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Worker(Consumer<E> consumer, BlockingQueue<Product<E>> queue) {
        this.consumer = consumer;
        this.queue = queue;
    }

}
