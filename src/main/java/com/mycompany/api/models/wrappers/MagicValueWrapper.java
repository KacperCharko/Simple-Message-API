package com.mycompany.api.models.wrappers;

import java.math.BigInteger;

public class MagicValueWrapper {
    BigInteger magic_number;

    public BigInteger getMagic_number() {
        return magic_number;
    }

    public void setMagic_number(BigInteger magic_number) {
        this.magic_number = magic_number;
    }

    public MagicValueWrapper(BigInteger magic_number) {
        this.magic_number = magic_number;
    }
}
