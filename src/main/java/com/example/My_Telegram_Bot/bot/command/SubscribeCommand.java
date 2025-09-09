package com.example.My_Telegram_Bot.bot.command;

import com.example.My_Telegram_Bot.model.Subscribers;
import com.example.My_Telegram_Bot.repositories.PriceRepository;
import com.example.My_Telegram_Bot.service.CryptoCurrencyService;
import com.example.My_Telegram_Bot.utils.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.UUID;

@Service
@Slf4j
public class SubscribeCommand implements IBotCommand {
    private  final PriceRepository priceRepository;
    private final CryptoCurrencyService service;

    public SubscribeCommand(PriceRepository priceRepository, CryptoCurrencyService service) {
        this.priceRepository = priceRepository;
        this.service = service;
    }

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            Long userId = message.getFrom().getId();
            Subscribers subscribers = priceRepository.findByTelegramUserID(userId).orElseThrow();
            UUID uuidUser = subscribers.getUuidUser();
            Subscribers subscribersNow = new Subscribers();
            subscribersNow.setUuidUser(uuidUser);
            subscribersNow.setTelegramUserID(userId);
            String regex = "[0-9]+";
            if(arguments[0].matches(regex)){
                Double d = Double.valueOf(arguments[0]);
                subscribersNow.setUserSubscriptionPrice(d);
                priceRepository.save(subscribersNow);
                answer.setText("Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD");
                absSender.execute(answer);
                answer.setText("Новая цена создана на стоимость " + arguments[0] + " USD");
                absSender.execute(answer);
            }else {
                answer.setText("Некорректный ввод стоимости биткоина");
                absSender.execute(answer);
            }
        } catch (Exception e) {
            //log.error("Error occurred in /start command", e);
        }
    }
}
