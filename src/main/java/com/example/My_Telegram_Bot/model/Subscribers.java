package com.example.My_Telegram_Bot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Subscribers")
public class Subscribers {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    //@GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuidUser;
    @Column(nullable = true)
    private Double userSubscriptionPrice;
}
