package com.example.My_Telegram_Bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

//@Getter
//@Setter
@Entity
@Table(name = "Subscribers")
public class Subscribers {
    @Id
    @Column(name = "uuidUser")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuidUser;
    @Column(name = "telegramUserID")
    private Long telegramUserID;
    @Column(name = "userSubscriptionPrice", nullable = true)
    private Double userSubscriptionPrice;

    public UUID getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(UUID uuidUser) {
        this.uuidUser = uuidUser;
    }

    public Long getTelegramUserID() {
        return telegramUserID;
    }

    public void setTelegramUserID(Long telegramUserID) {
        this.telegramUserID = telegramUserID;
    }
    public Double getUserSubscriptionPrice() {
        return userSubscriptionPrice;
    }
    public void setUserSubscriptionPrice(Double userSubscriptionPrice) {
        this.userSubscriptionPrice = userSubscriptionPrice;
    }
}
