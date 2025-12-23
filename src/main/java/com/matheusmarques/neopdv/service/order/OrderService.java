package com.matheusmarques.neopdv.service.order;

import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.build.OrderItemBuilder;
import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import com.matheusmarques.neopdv.api.order.request.DeleteItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderItemRequest;
import com.matheusmarques.neopdv.api.order.request.SalesStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderItemResponse;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.SalesStartResponse;
import com.matheusmarques.neopdv.exception.custom.*;
import com.matheusmarques.neopdv.mapper.order.OrderMap;
import com.matheusmarques.neopdv.repository.OrderItemRepository;
import com.matheusmarques.neopdv.repository.OrderRepository;
import com.matheusmarques.neopdv.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository repository, ProductRepository productRepository, OrderItemRepository orderItemRepository){
        this.repository = repository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderCardResponse> getCardOrders(){
        List<Order> orders = repository.findByStatus(StatusOrder.OPEN);
        List<OrderCardResponse> cardOrders = new ArrayList<>();

        if (orders.isEmpty()){return cardOrders;}

        return OrderMap.toOrderCardResponse(orders);
    }

    public List<Order> getAll(){
        List<Order> orders = repository.findByStatus(StatusOrder.OPEN);

        return orders;
    }

    public SalesStartResponse orderStart(SalesStartRequest request){
        Order orderRequest = OrderMap.map(request);
        if (repository.findByTableNumberAndStatus(orderRequest.getTableNumber(), StatusOrder.OPEN).isPresent()){
            throw new ValidationTableException();
        }
        Order orderSave = repository.save(orderRequest);
        return OrderMap.toSaleStartResponse(orderSave);
    }

    public OrderResponse getOrderById(String orderId){
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order não encontrada, id inválido"));

        List<OrderItem> itemList = orderItemRepository.findAllById(order.getItemsId());


        return OrderMap.toOrderResponse(order, itemList);
    }

    public OrderResponse removeItem(String orderId, DeleteItemRequest request){
        String itemId = request.itemId();
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order não encontrada, id inválido"));

        boolean itemRemoved = order.getItemsId().remove(itemId);

        if (!itemRemoved) {
            throw new ItemNotFoundException("ID do item não encontrado na lista: " + itemId);
        }

        OrderItem itemDelete = orderItemRepository.findById(itemId).orElseThrow();
        order.setAmount(order.getAmount().subtract(itemDelete.getAmount()));
        orderItemRepository.deleteById(itemId);

        return OrderMap.toOrderResponse(repository.save(order), orderItemRepository.findAllById(order.getItemsId()));
    }

    public OrderItemResponse addItem(OrderItemRequest request, String orderId){
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order não encontrada, id inválido"));

        if (!(order.getStatus() == StatusOrder.OPEN)){
            throw new StatusOrderException("O pedido não não está aberto");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product não encontrado, id inválido"));

        OrderItem orderItem = OrderItemBuilder.build(order, product, request);
        OrderItem orderItemSave = orderItemRepository.save(orderItem);

        product.setStock(product.getStock()-orderItemSave.getQuantity());

        order.setAmount(order.getAmount().add(orderItemSave.getAmount()));
        order.getItemsId().add(orderItemSave.getId());
        repository.save(order);

        return new OrderItemResponse(
                true,
                "Inserção feita com sucesso",
                orderItemSave.getId()
        );
    }

}
