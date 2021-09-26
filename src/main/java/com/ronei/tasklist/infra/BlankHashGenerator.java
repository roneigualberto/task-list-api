package com.ronei.tasklist.infra;

import com.ronei.tasklist.domain.user.HashGenerator;
import org.springframework.stereotype.Component;

@Component
public class BlankHashGenerator implements HashGenerator {

    @Override
    public String hash(String plainText) {
        return plainText;
    }
}
