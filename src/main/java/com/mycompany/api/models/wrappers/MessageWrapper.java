package com.mycompany.api.models.wrappers;

import java.math.BigInteger;
import java.util.Objects;

public class MessageWrapper {

    private String email;
    private String title;
    private String content;
    private BigInteger magic_number;

    public MessageWrapper(String email, String title, String content, BigInteger magic_number) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.magic_number = magic_number;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageWrapper)) return false;
        MessageWrapper that = (MessageWrapper) o;
        return email.equals(that.email) &&
                title.equals(that.title) &&
                content.equals(that.content) &&
                magic_number.equals(that.magic_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, title, content, magic_number);
    }
}
