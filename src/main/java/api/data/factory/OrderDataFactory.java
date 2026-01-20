package api.data.factory;

import api.models.store.Order;

import java.time.Instant;
import java.util.Random;

public class OrderDataFactory {

    private static final Random random = new Random();

    public static Order createRandomOrder() {
        Order order = new Order();
        order.id = generateId();
        order.petId = random.nextInt(100000);
        order.quantity = random.nextInt(5) + 1;
        order.shipDate = Instant.now().toString();
        order.status = "placed";
        order.complete = true;
        return order;
    }

    private static long generateId() {
        return System.currentTimeMillis();
    }
}
