package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Profile(Profiles.HSQL_DB)
public class HsqldbDateConverter implements DbDateConverter<Timestamp> {
    @Override
    public Timestamp getDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
