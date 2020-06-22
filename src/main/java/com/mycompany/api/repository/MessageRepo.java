package com.mycompany.api.repository;

import com.mycompany.api.models.Message;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.math.BigInteger;


public interface MessageRepo extends CassandraRepository<Message, String>{

    @Query("SELECT * FROM messages WHERE email = :emailValue;")
    Iterable<Message> findAllByEmail(String emailValue);

    @Query("SELECT * FROM messages WHERE magic_number = :value")
    Iterable<Message> findAllByMagicNumber(BigInteger value);
}