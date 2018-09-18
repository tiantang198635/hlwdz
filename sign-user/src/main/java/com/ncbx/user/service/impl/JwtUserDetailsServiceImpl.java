package com.ncbx.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncbx.user.dao.UserDao;

@Service
public class JwtUserDetailsServiceImpl/* implements UserDetailsService*/ {
    @Autowired
    private UserDao userDao;

    /*@Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }*/
}
