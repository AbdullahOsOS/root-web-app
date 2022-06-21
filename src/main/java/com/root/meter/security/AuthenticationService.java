package com.root.meter.security;

import com.root.meter.constants.Constants;
import com.root.meter.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WebClient webClient = WebClient.create(Constants.USER_API_FIND_BY_NAME +authentication.getName());
        Mono<ResponseEntity<Users>> res = webClient.get().retrieve().toEntity(Users.class);
        Users user = res.block().getBody();

        final Optional<Users> optionalUser = Optional.ofNullable(user);
        //check if the there's user attached with that username
        if(optionalUser.isPresent()){
            Users retrieved_user = optionalUser.get();
            String password = authentication.getCredentials().toString();
            //compare hashes
            if(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),retrieved_user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(authentication.getName(), password, new ArrayList<>());
            }
        }
        return null;    //not authenticated
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}