package com.app.subscriptiontracker.scheduler;

import com.app.subscriptiontracker.model.Subscription;
import com.app.subscriptiontracker.repository.SubscriptionRepository;
import com.app.subscriptiontracker.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReminderScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;

    public ReminderScheduler(SubscriptionRepository subscriptionRepository,
                             EmailService emailService) {
        this.subscriptionRepository = subscriptionRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Rulează în fiecare noapte la 00:00
    public void sendRenewalReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Subscription> upcoming = subscriptionRepository.findByBillingDate(tomorrow);

        for (Subscription sub : upcoming) {
            emailService.sendRenewalReminder(
                    sub.getUser().getEmail(),
                    sub.getName(),
                    sub.getAmount().doubleValue(),
                    sub.getCurrency()
            );
            System.out.println("Email trimis pentru: " + sub.getName());
        }
    }
}
