package io.alauda.producer;

import io.alauda.definition.Producer;
import io.alauda.definition.Product;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Worker<E> implements Producer<E>, Runnable {
    BlockingQueue<Product<E>> queue;
    Producer<E> producer;
    volatile boolean stop = false;

    @Override
    public Product<E> produce() {
        return this.producer.produce();
    }

    @Override
    public void run() {
        while (!stop) {
            doProduce();
        }
    }

    private void doProduce() {
        Product<E> product = this.producer.produce();
        try {
            this.queue.put(product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Worker(Producer<E> producer, BlockingQueue<Product<E>> queue) {
        this.producer = producer;
        this.queue = queue;
    }
}
