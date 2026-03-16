package com.app.subscriptiontracker.service;

import com.app.subscriptiontracker.model.Subscription;
import com.app.subscriptiontracker.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ExchangeRateService exchangeRateService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, ExchangeRateService exchangeRateService) {
        this.subscriptionRepository = subscriptionRepository;
        this.exchangeRateService = exchangeRateService;
    }

    public Map<String, Object> getSummary(Long userId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

        // Total pe lună (NOU: convertim fiecare sumă în RON înainte să o adunăm)
        BigDecimal monthlyTotal = subscriptions.stream()
                .filter(s -> s.getCycle().equals("MONTHLY"))
                .map(s -> exchangeRateService.convertToRon(s.getAmount(), s.getCurrency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Total anual (NOU: convertim fiecare sumă în RON)
        BigDecimal yearlyTotal = subscriptions.stream()
                .filter(s -> s.getCycle().equals("YEARLY"))
                .map(s -> exchangeRateService.convertToRon(s.getAmount(), s.getCurrency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(monthlyTotal.multiply(BigDecimal.valueOf(12)));

        // Cel mai scump abonament (Opțional: ca să fii 100% corect, aici poți compara tot suma convertită în RON)
        Optional<Subscription> mostExpensive = subscriptions.stream()
                .max(Comparator.comparing(s -> exchangeRateService.convertToRon(s.getAmount(), s.getCurrency())));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalSubscriptions", subscriptions.size());
        summary.put("monthlyTotal", monthlyTotal);
        summary.put("yearlyTotal", yearlyTotal);
        summary.put("mostExpensive", mostExpensive.map(Subscription::getName).orElse("N/A"));

        return summary;
    }

    // Trebuie să pui la loc și metodele pe care le-am omis mai sus (add, get, update, delete)
    public List<Subscription> getSubscriptionsByUser(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
    public Subscription addSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    public Subscription updateSubscription(Long id, Subscription updated) {
        Subscription existing = subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existing.setName(updated.getName());
        existing.setAmount(updated.getAmount());
        existing.setCurrency(updated.getCurrency());
        existing.setBillingDate(updated.getBillingDate());
        existing.setCycle(updated.getCycle());
        return subscriptionRepository.save(existing);
    }
    public void deleteSubscription(Long id, String userEmail) {
        Subscription existing = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonamentul nu a fost găsit"));
        if(!existing.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Nu ai permisiunea de a sterge aceasta subscriptie");
        }
        subscriptionRepository.deleteById(id);
    }
}