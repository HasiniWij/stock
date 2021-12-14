package com.example.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class StockApplicationTests {

    @Autowired
    private StockCalculationService service;

    private final List<Order> orderLine = new ArrayList<>() {{
        add(new Order("1", 50, "unit"));
        add(new Order("2", 40, "pack"));
        add(new Order("3", 30, "unit"));
        add(new Order("4", 10, "pack", true));
        add(new Order("5", 100, "unit", true));
        add(new Order("6", 62, "unit", true));
        add(new Order("7", 12, "pack", true));
    }};

    @Test
    public void getOrdersTest() {
        assertEquals(6, service.getOrders().size());
    }

    @Test
    public void getPriorityPackOrdersTest() {
        assertEquals(2, service.getPriorityPackOrders(orderLine).size());
    }

    @Test
    public void getPriorityPackOrdersEmptyArrayTest() {
        assertEquals(0, service.getPriorityPackOrders(new ArrayList<>()).size());
    }

    @Test
    public void getPriorityPackOrderNullTest() {
        assertEquals(0, service.getPriorityPackOrders(null).size());
    }

    @Test
    public void getPriorityUnitOrdersTest() {
        assertEquals(2, service.getPriorityUnitOrders(orderLine).size());
    }

    @Test
    public void getPriorityUnitOrdersEmptyArrayTest() {
        assertEquals(0, service.getPriorityUnitOrders(new ArrayList<>()).size());
    }

    @Test
    public void getPriorityUnitOrdersNullTest() {
        assertEquals(0, service.getPriorityUnitOrders(null).size());
    }

    @Test
    public void getPackOrdersTest() {
        assertEquals(1, service.getPackOrders(orderLine).size());
    }
    @Test
    public void getPackOrdersEmptyArrayTest() {
        assertEquals(0, service.getPackOrders(new ArrayList<>()).size());
    }

    @Test
    public void getPackOrdersNullTest() {
        assertEquals(0, service.getPackOrders(null).size());
    }

    @Test
    public void getUnitOrdersTest() {
        assertEquals(2, service.getUnitOrders(orderLine).size());
    }

    @Test
    public void getUnitOrdersArrayTest() {
        assertEquals(0, service.getUnitOrders(new ArrayList<>()).size());
    }

    @Test
    public void getUnitOrdersNullTest() {
        assertEquals(0, service.getUnitOrders(null).size());
    }

    @Test
    public void allocateStocksForOrderLineNoStockTest() {
        List<Order> expected = new ArrayList<>() {{
            add(new Order("1", 50, "unit"));
            add(new Order("2", 40, "pack"));
            add(new Order("3", 30, "unit"));
            add(new Order("4", 10, "pack", true));
            add(new Order("5", 100, "unit", true));
            add(new Order("6", 62, "unit", true));
            add(new Order("7", 12, "pack", true));
        }};
        assertEquals(expected, service.allocateStocksForOrderLine(this.orderLine, 0));
    }

    @Test
    public void allocateStocksForOrderLineInvalidStockTest() {
        List<Order> expected = new ArrayList<>() {{
            add(new Order("1", 50, "unit"));
            add(new Order("2", 40, "pack"));
            add(new Order("3", 30, "unit"));
            add(new Order("4", 10, "pack", true));
            add(new Order("5", 100, "unit", true));
            add(new Order("6", 62, "unit", true));
            add(new Order("7", 12, "pack", true));
        }};
        assertEquals(expected, service.allocateStocksForOrderLine(this.orderLine, -1));
    }

    @Test
    public void allocateStocksForOrderLineOverStockTest() {
        List<Order> expectedList = new ArrayList<>() {{
            add(new Order("1", 50, "unit", false, 0, 0));
            add(new Order("2", 40, "pack", false, 0, 0));
            add(new Order("3", 30, "unit", false, 0, 0));
            add(new Order("4", 10, "pack", true, 0, 0));
            add(new Order("5", 100, "unit", true, 0, 0));
            add(new Order("6", 62, "unit", true, 0, 0));
            add(new Order("7", 12, "pack", true, 0, 0));
        }};

        assertEquals(expectedList, service.allocateStocksForOrderLine(this.orderLine, 1000));
    }

    @Test
    public void allocateStocksForOrderLineNoOrdersTest() {
        assertEquals(new ArrayList<>(), service.allocateStocksForOrderLine(new ArrayList<>(), 300));
    }

    @Test
    public void allocateStocksForOrderLineNullAsOrdersTest() {
        assertNull(service.allocateStocksForOrderLine(null, 300));
    }

    @Test
    public void allocateStocksForOrderLineTest() {
        List<Order> expectedList = new ArrayList<>() {{
            add(new Order("1", 50, "unit", false, 100, 50));
            add(new Order("2", 40, "pack", false, 100, 40));
            add(new Order("3", 30, "unit", false, 100, 30));
            add(new Order("4", 10, "pack", true, 0, 0));
            add(new Order("5", 100, "unit", true, 50, 50));
            add(new Order("6", 62, "unit", true, 51, 32));
            add(new Order("7", 12, "pack", true, 0, 0));
        }};
        assertEquals(expectedList, service.allocateStocksForOrderLine(this.orderLine, 300));
    }
}




