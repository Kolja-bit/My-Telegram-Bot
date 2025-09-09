package com.example.My_Telegram_Bot.bot.command;

import com.example.My_Telegram_Bot.model.Subscribers;
import com.example.My_Telegram_Bot.repositories.PriceRepository;
import com.example.My_Telegram_Bot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
//@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {
    private  final PriceRepository priceRepository;

    public GetSubscriptionCommand(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            Long userId = message.getFrom().getId();
            Subscribers subscribers = priceRepository.findByTelegramUserID(userId).orElseThrow();
            Double userSubscriptionPrice = subscribers.getUserSubscriptionPrice();
            if (userSubscriptionPrice == null){
                answer.setText("Актуальные подписки отсутствуют");
            }else {
                answer.setText("Вы подписаны на стоимость биткоина "
                        + String.valueOf(userSubscriptionPrice) + " USD");
            }

            absSender.execute(answer);
        } catch (Exception e) {
            //log.error("Error occurred in /start command", e);
        }
    }
}
