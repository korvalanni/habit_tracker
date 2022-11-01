package com.example.habitstracker.security;

/**
 * Данные о клиенте, которые можно извлеч из JWT
 */
public record UserCredentials(long id, String username) {
}
