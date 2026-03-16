package com.app.subscriptiontracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele abonamentului este obligatoriu")
    @Size(min = 2, max = 50, message = "Numele trebuie să aibă între 2 și 50 caractere")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Suma este obligatorie")
    @Positive(message = "Suma trebuie să fie pozitivă")
    @Column(nullable = false)
    private BigDecimal amount;

    @NotBlank(message = "Moneda este obligatorie")
    @Column(nullable = false)
    private String currency;

    @NotNull(message = "Data de facturare este obligatorie")
    @Future(message = "Data de facturare trebuie să fie în viitor")
    @Column(nullable = false)
    private LocalDate billingDate;

    @NotBlank(message = "Ciclul este obligatoriu")
    @Pattern(regexp = "MONTHLY|YEARLY", message = "Ciclul trebuie să fie MONTHLY sau YEARLY")
    @Column(nullable = false)
    private String cycle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCycle() {
        return cycle;
    }

    public User getUser() {
        return user;
    }
}