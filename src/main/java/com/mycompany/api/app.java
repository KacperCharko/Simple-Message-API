package com.mycompany.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;


@SpringBootApplication(exclude = {CassandraAutoConfiguration.class})
public class app {
    public static void main(String[] args) {
        SpringApplication.run(app.class, args);
    }
}
