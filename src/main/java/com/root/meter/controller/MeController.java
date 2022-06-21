package com.root.meter.controller;

import com.root.meter.constants.Constants;
import com.root.meter.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/me")
public class MeController {
    @GetMapping
    public String get(Authentication auth, Model model){
        WebClient webClient = WebClient.create(Constants.USER_API_FIND_BY_NAME +auth.getName());
        Mono<ResponseEntity<Users>> res = webClient.get().retrieve().toEntity(Users.class);
        model.addAttribute("user",res.block().getBody());
        return "me";
    }
}
