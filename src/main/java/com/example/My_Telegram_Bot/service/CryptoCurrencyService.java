package com.example.My_Telegram_Bot.service;


import com.example.My_Telegram_Bot.client.BinanceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClient client;

    public CryptoCurrencyService(BinanceClient client) {
        this.client = client;
    }

    //@Scheduled(fixedDelay = 120000,timeUnit = TimeUnit.DAYS)
    //@Scheduled(fixedDelay = 120000)
    public double getBitcoinPrice() throws IOException {
        if (price.get() == null) {
            price.set(client.getBitcoinPrice());
        }
        //System.out.println(price.get());
        return price.get();
    }
}
