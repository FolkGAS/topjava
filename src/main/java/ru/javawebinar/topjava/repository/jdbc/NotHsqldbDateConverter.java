package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Component
@Profile(Profiles.POSTGRES_DB)
public class NotHsqldbDateConverter implements DbDateConverter<LocalDateTime> {
    @Override
    public LocalDateTime getDate(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
