package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.constants.Constants;
import com.root.meter.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class CheckoutController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @GetMapping("/checkout")
    public String checkout(Model model, Authentication auth) {
        //get the amount to be charged
        WebClient webClient = WebClient.create(Constants.USER_API_FIND_BY_NAME +auth.getName());
        Mono<ResponseEntity<Users>> res = webClient.get().retrieve().toEntity(Users.class);
        Users user = res.block().getBody();
        double amount = user.getMeter().getDebt(); //in dollars
        amount *=  100;
        model.addAttribute("userId",user.getId());
        model.addAttribute("amount", (int)amount); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "checkout";
    }
}
