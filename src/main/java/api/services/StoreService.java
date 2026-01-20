package api.services;

import api.base.BaseApi;
import api.endpoints.StoreEndpoints;
import api.models.store.Order;
import io.restassured.response.Response;

public class StoreService extends BaseApi {

    public StoreService(String baseUrl) {
        super(baseUrl);
    }

    public Response placeOrder(Order order) {
        return request
                .body(order)
                .post(StoreEndpoints.PLACE_ORDER);
    }

    public Response getInventory() {
        return request.get(StoreEndpoints.INVENTORY);
    }

    public Response getOrderById(long orderId) {
        return request
                .pathParam("orderId", orderId)
                .get(StoreEndpoints.ORDER_BY_ID);
    }

    public Response deleteOrder(long orderId) {
        return request
                .pathParam("orderId", orderId)
                .delete(StoreEndpoints.ORDER_BY_ID);
    }
}
