package com.mycompany.api.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    String keyspace = "CREATE KEYSPACE IF NOT EXISTS message WITH REPLICATION = "
            + "{ 'class' : 'SimpleStrategy', 'replication_factor' : 1 }";

    String query = "CREATE TABLE IF NOT EXISTS messages (id text, "
            + "email text, "
            + "title text, "
            + "content text, "
            + "magic_number varint, "
            + "PRIMARY KEY (id,email, magic_number)) WITH default_time_to_live = 300;";

    String indexNumber = "CREATE INDEX IF NOT EXISTS ON messages(magic_number);";
    String indexEmail = "CREATE INDEX IF NOT EXISTS ON messages(email);";

    public @Bean
    CqlSession session() {
        return CqlSession.builder().withKeyspace("message").build();
    }

    @PostConstruct
    void init() {
        CqlSession cqlSession = CqlSession.builder().build();

        cqlSession.execute(keyspace);

        cqlSession = CqlSession
                .builder()
                .withKeyspace("message")
                .build();

        cqlSession.execute(query);
        cqlSession.execute(indexNumber);
        cqlSession.execute(indexEmail);
        cqlSession.close();

    }

}