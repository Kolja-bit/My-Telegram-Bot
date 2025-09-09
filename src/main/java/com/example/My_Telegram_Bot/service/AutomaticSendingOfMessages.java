package com.example.My_Telegram_Bot.service;

import com.example.My_Telegram_Bot.model.Subscribers;
import com.example.My_Telegram_Bot.repositories.PriceRepository;
import com.example.My_Telegram_Bot.utils.TextUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;


import java.io.IOException;
import java.util.List;

@Service
public class AutomaticSendingOfMessages {
    private final CryptoCurrencyService service;
    private  final PriceRepository priceRepository;
    private final AbsSender absSender;

    public AutomaticSendingOfMessages(CryptoCurrencyService service,
                                      PriceRepository priceRepository, AbsSender absSender) {
        this.service = service;
        this.priceRepository = priceRepository;
        this.absSender = absSender;
    }
    @Scheduled(fixedDelay = 10000)
    public void getCurrentPriceOfBitcoin(){
        try {
            service.getBitcoinPrice();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Subscribers> getListOfSuitableSubscriptions(){
        List<Subscribers> listSubscribers = null;
        try {
            listSubscribers = priceRepository
                    .findAllSubscriptionPrice(service.getBitcoinPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listSubscribers;
    }
    @Scheduled(fixedDelay = 60000)
    public void sendingMessage() {
        for (Subscribers subscribers:getListOfSuitableSubscriptions()){
            try {
                Long userId = subscribers.getTelegramUserID();
                SendMessage answer = new SendMessage();
                answer.setChatId(userId);
                answer.setText("Пора покупать, стоимость биткоина "
                        + TextUtil.toString(service.getBitcoinPrice()) + " USD");
                absSender.execute(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
