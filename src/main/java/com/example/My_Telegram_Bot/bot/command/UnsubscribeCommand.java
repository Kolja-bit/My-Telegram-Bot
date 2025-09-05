package com.example.My_Telegram_Bot.bot.command;

import com.example.My_Telegram_Bot.model.Subscribers;
import com.example.My_Telegram_Bot.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.UUID;

@Service
@Slf4j
//@AllArgsConstructor
public class UnsubscribeCommand implements IBotCommand {
    private  final PriceRepository priceRepository;

    public UnsubscribeCommand(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            Long userId = message.getFrom().getId();
            Subscribers subscribers = priceRepository.findByTelegramUserID(userId).orElseThrow();
            UUID uuidUser = subscribers.getUuidUser();
            String userSubscriptionPrice = subscribers.getUserSubscriptionPrice();
            if (userSubscriptionPrice.equals("null")){
                answer.setText("Актуальные подписки отсутствуют");
                absSender.execute(answer);
            }else {
                Subscribers subscribersNow = new Subscribers();
                subscribersNow.setUuidUser(uuidUser);
                subscribersNow.setTelegramUserID(userId);
                subscribersNow.setUserSubscriptionPrice("null");
                priceRepository.save(subscribersNow);
            }
            answer.setText("Подписка отменена");
            absSender.execute(answer);
        } catch (Exception e) {
            //log.error("Error occurred in /start command", e);
        }
    }
}
