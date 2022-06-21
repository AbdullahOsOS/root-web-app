package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.constants.Constants;
import com.root.meter.model.Bill;
import com.root.meter.model.Payment;
import com.root.meter.model.Users;
import com.root.meter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Controller
public class BillController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/me/bill")
    public String generateBill(Authentication auth, Model model){
        WebClient webClientOfUser = WebClient.create(Constants.USER_API_FIND_BY_NAME +auth.getName());
        Mono<ResponseEntity<Users>> res = webClientOfUser.get().retrieve().toEntity(Users.class);
        Users user = res.block().getBody();
        Payment payment = paymentService.get(user.getId());
        LocalDate lastPaymentDate = null;
        if(payment != null){
            lastPaymentDate = payment.getPaymentDate();
        }
        Bill bill = new Bill(user,
                             user.getMeter().getEnergyDebt(),
                            user.getMeter().getDebt()*100,
                            lastPaymentDate
                            );
        model.addAttribute("bill",bill);
        model.addAttribute("userId", user.getId());
        model.addAttribute("amount", (int)(user.getMeter().getDebt()*100)); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "bill";
    }
}
