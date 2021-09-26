package com.ronei.tasklist.domain.user;

public interface HashGenerator {

    String hash(String plainText);
}
