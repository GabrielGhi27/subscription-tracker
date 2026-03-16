package com.app.subscriptiontracker.controller;


import com.app.subscriptiontracker.model.Subscription;
import com.app.subscriptiontracker.scheduler.ReminderScheduler;
import com.app.subscriptiontracker.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final ReminderScheduler reminderScheduler;

    public SubscriptionController(SubscriptionService subscriptionService, ReminderScheduler reminderScheduler) {
        this.subscriptionService = subscriptionService;
        this.reminderScheduler = reminderScheduler;
    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity<Map<String, Object>> getSummary(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getSummary(userId));
    }

    @GetMapping("/test-reminder")
    public String testReminder() {
        reminderScheduler.sendRenewalReminders();
        return "Job rulat!";
    }

    // GET toate abonamentele unui user
    @GetMapping("/user/{userId}")
    public List<Subscription> getByUser(@PathVariable Long userId) {
        return subscriptionService.getSubscriptionsByUser(userId);
    }

    // POST adaugă abonament nou
    @PostMapping
    public ResponseEntity<Subscription> add(@Valid @RequestBody Subscription subscription) {
        return ResponseEntity.ok(subscriptionService.addSubscription(subscription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> update(@PathVariable Long id,
                                               @Valid @RequestBody Subscription subscription) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, subscription));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        String emailUtilizatorLogat =  principal.getName();
        subscriptionService.deleteSubscription(id, emailUtilizatorLogat);
        return ResponseEntity.noContent().build();
    }


}