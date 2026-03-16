package com.app.subscriptiontracker.repository;

import com.app.subscriptiontracker.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByBillingDate(LocalDate date); // folosit de Cron Job
}