package com.ncbx.util;

import com.ncbx.entity.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId().toString(),
                user.getName(),
                user.getPwd(),
                user.getCreateTime()
        );
    }
}

