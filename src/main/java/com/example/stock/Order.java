package com.example.stock;

import java.util.Objects;

public class Order {

    private String id;
    private boolean priority;
    private int order_quantity;
    private int remaining_percentage;
    private int remaining_quantity;

    public Order() {
        this.remaining_percentage = 100;
    }

    private String pgk_type;

    public Order(String id, int order_quantity, String pgk_type, boolean priority) {
        this.id = id;
        this.priority = priority;
        this.order_quantity = order_quantity;
        this.remaining_percentage = 100;
        this.remaining_quantity = order_quantity;
        this.pgk_type = pgk_type;
    }

    public Order(String id,int order_quantity, String pgk_type,boolean priority, int remaining_percentage, int remaining_quantity) {
        this.id = id;
        this.priority = priority;
        this.order_quantity = order_quantity;
        this.remaining_percentage = remaining_percentage;
        this.remaining_quantity = remaining_quantity;
        this.pgk_type = pgk_type;
    }

    public Order(String id, int order_quantity, String pgk_type) {
        this.id = id;
        this.order_quantity = order_quantity;
        this.remaining_percentage = 100;
        this.remaining_quantity = order_quantity;
        this.pgk_type = pgk_type;
    }

    public String getId() {
        return id;
    }

    public boolean isPriority() {
        return priority;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public int getRemaining_percentage() {
        return remaining_percentage;
    }

    public int getRemaining_quantity() {
        return remaining_quantity;
    }

    public String getPgk_type() {
        return pgk_type;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public void setRemaining_quantity_percentage() {
        this.remaining_percentage = (this.remaining_quantity * 100) / this.order_quantity;
    }

    public void setUpdated_quantity() {
        this.remaining_quantity = this.remaining_quantity -1;
    }

    public void setPgk_type(String pgk_type) {
        this.pgk_type = pgk_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return priority == order.priority && order_quantity == order.order_quantity && remaining_percentage == order.remaining_percentage && remaining_quantity == order.remaining_quantity && Objects.equals(id, order.id) && Objects.equals(pgk_type, order.pgk_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, order_quantity, remaining_percentage, remaining_quantity, pgk_type);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", priority=" + priority +
                ", order_quantity=" + order_quantity +
                ", remaining_percentage=" + remaining_percentage +
                ", remaining_quantity=" + remaining_quantity +
                ", pgk_type='" + pgk_type + '\'' +
                '}';
    }

}
