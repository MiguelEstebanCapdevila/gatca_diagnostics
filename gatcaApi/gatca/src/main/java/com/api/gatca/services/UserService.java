package com.api.gatca.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  /*  User getUserByUsername(String username);
    public UserDetails loadUserByUsername(String username);


   */

    UserDetailsService userDetailsService();

}
