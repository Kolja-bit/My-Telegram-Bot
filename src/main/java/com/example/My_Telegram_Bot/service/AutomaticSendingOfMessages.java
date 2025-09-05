package com.example.My_Telegram_Bot.service;

import com.example.My_Telegram_Bot.model.Subscribers;
import com.example.My_Telegram_Bot.repositories.PriceRepository;
import com.example.My_Telegram_Bot.utils.TextUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Service
public class AutomaticSendingOfMessages {
    private final CryptoCurrencyService service;
    private  final PriceRepository priceRepository;

    public AutomaticSendingOfMessages(CryptoCurrencyService service, PriceRepository priceRepository) {
        this.service = service;
        this.priceRepository = priceRepository;
    }
    public String getCurrentPriceOfBitcoin(){
        String priceBitcoin = "";
        try {
            priceBitcoin = TextUtil.toString(service.getBitcoinPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceBitcoin;
    }
    public List<Subscribers> getListOfSuitableSubscriptions(){
        List<Subscribers> listSubscribers = priceRepository
                .findAllByUserSubscriptionPrice(getCurrentPriceOfBitcoin());
        return listSubscribers;
    }
    @Scheduled(fixedDelay = 60000)
    public void sendingMessage(AbsSender absSender){
        for (Subscribers subscribers:getListOfSuitableSubscriptions()){
            Long userId = subscribers.getTelegramUserID();
            SendMessage answer = new SendMessage();
            answer.setChatId(userId);
            answer.setText("Пора покупать, стоимость биткоина " + getCurrentPriceOfBitcoin() + " USD");
            try {
                absSender.execute(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
