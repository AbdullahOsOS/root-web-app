package com.root.meter.security;


import com.root.meter.constants.Constants;
import com.root.meter.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * adapted from the supporting material
 * can be found at https://classroom.udacity.com/nanodegrees/nd035/parts/ef7ed085-afaf-4e13-924d-94c9746ed954/modules/c48ee8c7-bbce-4e82-8607-650c5acac401/lessons/6937a8f4-aeb7-4aec-ac7b-37840ecf1765/concepts/f4a66010-25d7-41a2-9266-6b96e5da2646
 * date:16/9/2021
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebClient webClient = WebClient.create(Constants.USER_API_FIND_BY_NAME +username);
        Mono<ResponseEntity<Users>> res = webClient.get().retrieve().toEntity(Users.class);
        Users user = res.block().getBody();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        else return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), Collections.emptyList());
    }
}
