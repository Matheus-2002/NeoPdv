package com.matheusmarques.neopdv.build;

import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.api.order.request.ItemRequest;

import java.math.BigDecimal;

public class OrderItemBuilder {
    public static OrderItem build(Order order, Product product, ItemRequest request){

        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(product.getValue().multiply(new BigDecimal(request.quantity())));
        orderItem.setProductPrice(product.getValue());
        orderItem.setProductName(product.getName());
        orderItem.setQuantity(request.quantity());
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());

        return orderItem;
    }
}
