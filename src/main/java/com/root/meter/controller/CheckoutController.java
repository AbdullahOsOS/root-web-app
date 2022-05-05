package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model,@RequestParam("userId") Long userId) {
        //get the amount to be charged
        double amount = userService.getUserConsumption(userId); //in dollars
        amount *=  100;
        model.addAttribute("userId",userId);
        model.addAttribute("amount", (int)amount); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "checkout";
    }
}