package com.example.My_Telegram_Bot.repositories;

import com.example.My_Telegram_Bot.model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Subscribers,Integer> {
}
