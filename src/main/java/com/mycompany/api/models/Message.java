package com.mycompany.api.models;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;

@Table("messages")
public class Message {

    @PrimaryKey
    private String id;
    private final String email;
    private final String title;
    private final String content;
    private final BigInteger magic_number;

    public Message(String email, String title, String content, BigInteger magic_number) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.magic_number = magic_number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public BigInteger getMagic_number() {
        return magic_number;
    }

    public boolean hasNullValue() {
        if (getEmail() == null) {
            return true;
        }
        if (getContent() == null) {
            return true;
        }
        if (getMagic_number() == null) {
            return true;
        }
        if (getTitle() == null) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Message{" +
                "email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}


