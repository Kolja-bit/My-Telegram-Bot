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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.api.objects.Update;
@Service
//@AllArgsConstructor
@Slf4j
public class StartCommand implements IBotCommand {
    private  final PriceRepository priceRepository;

    public StartCommand(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        answer.setText("""
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /get_price - получить стоимость биткоина
                 /start - регистрация нового пользователя
                """);
        try {
            Subscribers subscribers = new Subscribers();
            Long userId = message.getFrom().getId();
            subscribers.setTelegramUserID(userId);
            subscribers.setUserSubscriptionPrice("null");
            priceRepository.save(subscribers);
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            //log.error("Error occurred in /start command", e);
        }
    }

}
