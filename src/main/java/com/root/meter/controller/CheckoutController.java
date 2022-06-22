package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.MeterDTO;
import com.root.meter.DTO.UserDTO;
import com.root.meter.constants.Constants;
import com.root.meter.model.Meter;
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
        Mono<ResponseEntity<UserDTO>> res = webClient.get().retrieve().toEntity(UserDTO.class);
        UserDTO user = res.block().getBody();

        WebClient webClientOfMeter = WebClient.create(Constants.METER_API_GET_BY_ID + user.getMeterId());
        Mono<ResponseEntity<MeterDTO>> meterMono = webClientOfMeter.get().retrieve().toEntity(MeterDTO.class);
        MeterDTO meter = meterMono.block().getBody();

        double amount = meter.getDebt(); //in dollars
        amount *=  100;
        model.addAttribute("userId",user.getId());
        model.addAttribute("amount", (int)amount); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "checkout";
    }
}
