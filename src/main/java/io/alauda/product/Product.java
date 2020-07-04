package io.alauda.product;

public class Product<E> implements io.alauda.definition.Product {
    private E data;

    @Override
    public Object get() {
        return this.data;
    }

    public Product(E data) {
        this.data = data;
    }
}
