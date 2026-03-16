package com.app.subscriptiontracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExchangeRateService {

    private static final String API_URL = "https://open.er-api.com/v6/latest/RON";
    private final RestTemplate restTemplate;
    private Map<String, Double> rates;

    public ExchangeRateService() {
        this.restTemplate = new RestTemplate();
        fetchRates();
    }

    private void fetchRates() {
        try {
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
            if (response != null && response.containsKey("rates")) {
                this.rates = (Map<String, Double>) response.get("rates");
                System.out.println("Cursuri valutare actualizate cu succes!");
            }
        } catch (Exception e) {
            System.err.println("Eroare la aducerea cursurilor valutare: " + e.getMessage());
        }
    }

    public BigDecimal convertToRon(BigDecimal amount, String currency) {
        if ("RON".equalsIgnoreCase(currency)) {
            return amount;
        }

        if (rates != null && rates.containsKey(currency.toUpperCase())) {
            double exchangeRate = rates.get(currency.toUpperCase());
            return amount.divide(BigDecimal.valueOf(exchangeRate), 2, RoundingMode.HALF_UP);
        }

        return amount;
    }
}