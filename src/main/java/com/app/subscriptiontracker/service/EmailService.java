package com.app.subscriptiontracker.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendRenewalReminder(String toEmail, String subscriptionName, double amount, String currency) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reminder: " + subscriptionName + " se reînnoiește mâine!");
        message.setText(
                "Bună!\n\n" +
                        "Abonamentul tău la " + subscriptionName + " se reînnoiește mâine.\n" +
                        "Suma: " + amount + " " + currency + "\n\n" +
                        "Subscription Tracker"
        );
        mailSender.send(message);
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail); // Dinamica - trimite pe emailul primit ca parametru
        message.setSubject("Bun venit la SubTracker, " + name + "! 🎉");
        message.setText(
                "Salut, " + name + ",\n\n" +
                        "Ne bucurăm să te avem alături! De azi poți să îți gestionezi ușor toate abonamentele și să nu mai pierzi bani pe reînnoiri uitate.\n\n" +
                        "Spor la economisit,\n" +
                        "Echipa SubTracker"
        );
        mailSender.send(message);
        System.out.println("Email de bun venit trimis către: " + toEmail);
    }
}