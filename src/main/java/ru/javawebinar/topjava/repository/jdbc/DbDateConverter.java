package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

public interface DbDateConverter<T> {
    T getDate (LocalDateTime localDateTime);
}
