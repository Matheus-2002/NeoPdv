package com.matheusmarques.neopdv.service.order.impl;

import com.matheusmarques.neopdv.api.order.request.ItemDeleteRequest;
import com.matheusmarques.neopdv.api.order.request.ItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;
import com.matheusmarques.neopdv.api.order.response.*;
import com.matheusmarques.neopdv.build.OrderItemBuilder;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.product.Product;
import com.matheusmarques.neopdv.exception.custom.*;
import com.matheusmarques.neopdv.mapper.order.OrderMap;
import com.matheusmarques.neopdv.repository.OrderItemRepository;
import com.matheusmarques.neopdv.repository.OrderRepository;
import com.matheusmarques.neopdv.repository.ProductRepository;
import com.matheusmarques.neopdv.service.order.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository repository, ProductRepository productRepository, OrderItemRepository orderItemRepository){
        this.repository = repository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public QuantityOrdersOpenResponse getQuantityOpenOders(){

        List<Order> orders = repository.findByStatus(StatusOrder.OPEN);

        if(orders.isEmpty()){return new QuantityOrdersOpenResponse(0);}

        return new QuantityOrdersOpenResponse(orders.size());
    }

    @Override
    public SalesTodayResponse getSalesToday(){

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<Order> orders = repository.findByCreatedAtBetweenAndStatus(start, end, StatusOrder.CLOSED);

        if(orders.isEmpty()){return new SalesTodayResponse(0);}

        return new SalesTodayResponse(orders.size());
    }

    @Override
    public AmountTodayResponse getAmountToday(){

        BigDecimal amountToday = BigDecimal.ZERO;

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<Order> orders = repository.findByCreatedAtBetweenAndStatus(start, end, StatusOrder.CLOSED);

        if(orders.isEmpty()){return new AmountTodayResponse(amountToday);}


        for (Order order : orders) {
            amountToday = amountToday.add(order.getAmount());
        }
        return new AmountTodayResponse(amountToday);
    }

    @Override
    public List<OrderCardResponse> getAllOpen(){
        List<Order> orders = repository.findByStatus(StatusOrder.OPEN);

        return OrderMap.toOrderCardResponse(orders);
    }

    @Override
    public OrderStartResponse orderStart(OrderStartRequest request){
        Order orderRequest = OrderMap.map(request);
        if (repository.findByTicketAndStatus(orderRequest.getTicket(), StatusOrder.OPEN).isPresent()){
            throw new ValidationTableException();
        }
        Order orderSave = repository.save(orderRequest);
        return OrderMap.toOrderStartResponse(orderSave);
    }

    @Override
    public OrderResponse getOrderById(String orderId){
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order não encontrada, id inválido"));

        List<OrderItem> itemList = orderItemRepository.findAllById(order.getItemsId());


        return OrderMap.toOrderResponse(order, itemList);
    }

    @Override
    public OrderResponse removeItem(String orderId, ItemDeleteRequest request){
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

    @Override
    public ItemResponse addItem(ItemRequest request, String orderId){
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

        return new ItemResponse(
                true,
                "Inserção feita com sucesso",
                orderItemSave.getId()
        );
    }
}
