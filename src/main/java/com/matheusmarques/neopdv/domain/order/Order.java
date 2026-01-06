package com.matheusmarques.neopdv.domain.order;

import com.matheusmarques.neopdv.domain.enums.PaymentMethod;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tb_orders")
public class Order {

    @MongoId
    private String id;
    private String owner;
    private String customer;
    @Min(value = 1, message = "Valor do ticket deve ser maior que 0")
    private int ticket;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    @NotBlank
    private StatusOrder status;
    private LocalDateTime createdDate;
    private List<String> itemsId;

    public Order() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getItemsId() {
        return itemsId;
    }

    public void setItemsId(List<String> itemsId) {
        this.itemsId = itemsId;
    }
}
