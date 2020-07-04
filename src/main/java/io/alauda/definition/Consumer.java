package io.alauda.definition;

public interface Consumer<E> {
    void consume(Product<E> product);
}
