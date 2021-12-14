package com.example.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockCalculationService {
    private int stock;

    public List<Order> getOrders() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Order[] orders = mapper.readValue(new File("C:\\Users\\hasiniw\\Downloads\\stock\\stock\\src\\main\\java\\com\\example\\stock\\data.json"), Order[].class);
            return Arrays.asList(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getPriorityPackOrders(List<Order> orderLine) {
        try {
            return orderLine.stream()
                    .filter(p -> Objects.equals(p.getPgk_type(), "pack") && p.isPriority() && p.getRemaining_percentage() != 0).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public List<Order> getPriorityUnitOrders(List<Order> orderLine) {
        try {
            return orderLine.stream()
                    .filter(p -> Objects.equals(p.getPgk_type(), "unit") && p.isPriority() && p.getRemaining_percentage() != 0).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Order> getPackOrders(List<Order> orderLine) {
        try {
            return orderLine.stream()
                    .filter(p -> Objects.equals(p.getPgk_type(), "pack") && !p.isPriority() && p.getRemaining_percentage() != 0).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Order> getUnitOrders(List<Order> orderLine) {
        try {
            return orderLine.stream()
                    .filter(p -> Objects.equals(p.getPgk_type(), "unit") && !p.isPriority() && p.getRemaining_percentage() != 0).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void allocateStock(List<Order> remainingPriorityOrders, boolean isPack) {
        if (remainingPriorityOrders.size() != 0 && ((stock > 9 && isPack) || (stock > 0 && !isPack))) {
            Order order = Collections.max(remainingPriorityOrders, Comparator.comparing(Order::getRemaining_percentage));
            order.setUpdated_quantity();
            order.setRemaining_quantity_percentage();
            if (Objects.equals(order.getPgk_type(), "unit")) stock--;
            else stock = stock - 10;
            if (order.getRemaining_quantity() == 0) remainingPriorityOrders.remove(order);
            allocateStock(remainingPriorityOrders, isPack);
        }
    }

    public List<Order> allocateStocksForOrderLine(List<Order> orderLine, int stock) {
        this.stock = stock;
        if (this.stock > 9) allocateStock(getPriorityPackOrders(orderLine), true);
        if (this.stock > 0) allocateStock(getPriorityUnitOrders(orderLine), false);
        if (this.stock > 9) allocateStock(getPackOrders(orderLine), true);
        if (this.stock > 0) allocateStock(getUnitOrders(orderLine), false);

        return orderLine;
    }
}
