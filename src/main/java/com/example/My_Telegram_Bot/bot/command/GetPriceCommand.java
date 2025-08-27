package com.example.My_Telegram_Bot.bot.command;

import com.example.My_Telegram_Bot.service.CryptoCurrencyService;
import com.example.My_Telegram_Bot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
//@AllArgsConstructor
public class GetPriceCommand implements IBotCommand {

    private final CryptoCurrencyService service;

    public GetPriceCommand(CryptoCurrencyService service) {
        this.service = service;
    }

    @Override
    public String getCommandIdentifier() {
        return "get_price";
    }

    @Override
    public String getDescription() {
        return "Возвращает цену биткоина в USD";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            answer.setText("Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD");
            absSender.execute(answer);
        } catch (Exception e) {
            //log.error("Ошибка возникла /get_price методе", e);
        }
    }
}
