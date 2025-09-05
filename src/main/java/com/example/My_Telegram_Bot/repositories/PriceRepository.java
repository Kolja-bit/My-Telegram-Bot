package com.example.My_Telegram_Bot.repositories;

import com.example.My_Telegram_Bot.model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Subscribers, UUID> {
    Optional<Subscribers> findByTelegramUserID(Long l);
    @Query(value = "SELECT userSubscriptionPrice FROM Subscribers WHERE userSubscriptionPrice > s")
    List<Subscribers> findAllByUserSubscriptionPrice(String s);
}
