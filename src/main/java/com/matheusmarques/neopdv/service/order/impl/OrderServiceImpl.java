package com.matheusmarques.neopdv.service.order.impl;

import com.matheusmarques.neopdv.api.order.request.CloseOrderRequest;
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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<OrderSummaryResponse> getAllOrderSummary(){
        return OrderMap.toOrderSummaryResponse(repository.findAll());
    }

    public QuantityOrdersOpenResponse getQuantityOpenOders(){
        return new QuantityOrdersOpenResponse(repository.findByStatus(StatusOrder.OPEN).size());
    }

    public SalesTodayResponse getSalesToday(){
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        return new SalesTodayResponse(
                repository.findByCreatedAtBetweenAndStatus(start, end, StatusOrder.CLOSED).size()
        );
    }

    public AmountTodayResponse getAmountToday(){
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        BigDecimal total = repository
                .findByCreatedAtBetweenAndStatus(start, end, StatusOrder.CLOSED)
                .stream()
                .map(Order::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AmountTodayResponse(total);
    }

    public List<OrderSummaryResponse> getAllOpenOderSummary(){
        return OrderMap.toOrderSummaryResponse(repository.findByStatus(StatusOrder.OPEN));
    }

    @Transactional
    public OrderStartResponse orderStart(OrderStartRequest request){
        Order order = OrderMap.map(request);

        repository.findByTicketAndStatus(order.getTicket(), StatusOrder.OPEN)
                .ifPresent(o -> { throw new ValidationTableException(); });

        return OrderMap.toOrderStartResponse(repository.save(order));
    }

    public OrderResponse getOrderById(String orderId){
        Order order = findOrder(orderId);
        List<OrderItem> items = orderItemRepository.findAllById(order.getItemsId());
        return OrderMap.toOrderResponse(order, items);
    }

    @Transactional
    public OrderResponse removeItem(String orderId, ItemDeleteRequest request){
        Order order = findOrder(orderId);

        if (!order.getItemsId().remove(request.itemId())){
            throw new ItemNotFoundException("Item não encontrado");
        }

        OrderItem item = orderItemRepository.findById(request.itemId())
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado"));

        Product product = findProduct(item.getProductId());

        product.setStock(product.getStock() + item.getQuantity());
        order.setAmount(order.getAmount().subtract(item.getAmount()));

        productRepository.save(product);
        orderItemRepository.delete(item);

        return OrderMap.toOrderResponse(repository.save(order), orderItemRepository.findAllById(order.getItemsId()));
    }

    @Transactional
    public ItemResponse addItem(ItemRequest request, String orderId){
        Order order = validateOpenOrder(orderId);
        Product product = findProduct(request.productId());

        if (product.getStock() < request.quantity()){
            throw new InsufficientStockException("Item sem estoque");
        }

        List<OrderItem> items = orderItemRepository.findByOrderIdAndProductId(order.getId(), product.getId());

        if (items.isEmpty()){
            OrderItem item = orderItemRepository.save(OrderItemBuilder.build(order, product, request));

            product.setStock(product.getStock() - item.getQuantity());
            order.setAmount(order.getAmount().add(item.getAmount()));
            order.getItemsId().add(item.getId());

            productRepository.save(product);
            repository.save(order);

            return new ItemResponse(true, "OK", item.getId());
        }

        OrderItem item = items.getFirst();
        BigDecimal amount = product.getValue().multiply(BigDecimal.valueOf(request.quantity()));

        item.setQuantity(item.getQuantity() + request.quantity());
        item.setAmount(item.getAmount().add(amount));

        product.setStock(product.getStock() - request.quantity());
        order.setAmount(order.getAmount().add(amount));

        orderItemRepository.save(item);
        productRepository.save(product);
        repository.save(order);

        return new ItemResponse(true, "OK", item.getId());
    }

    @Transactional
    public OrderSummaryResponse closedOrder(String orderId, CloseOrderRequest request){
        Order order = findOrder(orderId);

        if (order.getStatus() == StatusOrder.CLOSED){
            throw new OrderAlreadyClosedException("Pedido já foi fechado");
        }

        order.setPaymentMethod(request.paymentMethod());
        order.setStatus(StatusOrder.CLOSED);
        return OrderMap.toOrderSummaryResponse(repository.save(order));
    }

    @Transactional
    public ItemResponse subItem(ItemRequest request, String orderId){
        Order order = validateOpenOrder(orderId);
        Product product = findProduct(request.productId());

        OrderItem item = orderItemRepository
                .findByOrderIdAndProductId(order.getId(), product.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item não encontrado"));

        int newQuantity = item.getQuantity() - request.quantity();

        if (newQuantity < 0){
            throw new InvalidQuantityException("Subtração invalida");
        }

        BigDecimal amount = product.getValue().multiply(BigDecimal.valueOf(request.quantity()));

        product.setStock(product.getStock() + request.quantity());

        if (newQuantity == 0){
            order.setAmount(order.getAmount().subtract(item.getAmount()));
            order.getItemsId().remove(item.getId());
            orderItemRepository.delete(item);
        } else {
            item.setQuantity(newQuantity);
            item.setAmount(item.getAmount().subtract(amount));
            order.setAmount(order.getAmount().subtract(amount));
            orderItemRepository.save(item);
        }

        productRepository.save(product);
        repository.save(order);

        return new ItemResponse(true, "OK", item.getId());
    }

    public TopProductMonthResponse getTopProductMonth(){
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime start = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime end = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<String> allItemIds = repository
                .findByCreatedAtBetweenAndStatus(start, end, StatusOrder.CLOSED)
                .stream()
                .flatMap(order -> order.getItemsId().stream())
                .toList();

        if (allItemIds.isEmpty()){
            return new TopProductMonthResponse(null, null, 0);
        }

        return orderItemRepository.findAllById(allItemIds)
                .stream()
                .collect(Collectors.groupingBy(
                        item -> Map.entry(item.getProductId(), item.getProductName()),
                        Collectors.summingInt(OrderItem::getQuantity)
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(e -> new TopProductMonthResponse(e.getKey().getValue(), e.getKey().getKey(), e.getValue()))
                .orElse(new TopProductMonthResponse(null, null, 0));
    }

    private Order findOrder(String id){
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order não encontrada"));
    }

    private Product findProduct(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
    }

    private Order validateOpenOrder(String id){
        Order order = findOrder(id);

        if (order.getStatus() != StatusOrder.OPEN){
            throw new StatusOrderException("Pedido fechado");
        }

        return order;
    }
}