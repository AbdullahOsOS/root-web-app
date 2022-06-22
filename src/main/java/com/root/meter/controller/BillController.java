package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.PaymentView;
import com.root.meter.DTO.UserDTO;
import com.root.meter.constants.Constants;
import com.root.meter.model.Bill;
import com.root.meter.model.Meter;
import com.root.meter.model.Payment;
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

    @GetMapping("/me/bill")
    public String generateBill(Authentication auth, Model model){
        WebClient webClientOfUser = WebClient.create(Constants.USER_API_FIND_BY_NAME +auth.getName());
        Mono<ResponseEntity<UserDTO>> res = webClientOfUser.get().retrieve().toEntity(UserDTO.class);
        UserDTO user = res.block().getBody();

        WebClient paymentClient = WebClient.create(Constants.GET_LAST_PAYMENT +user.getId());
        Mono<ResponseEntity<Payment>> paymentMono = paymentClient.get().retrieve().toEntity(Payment.class);

        LocalDate lastPaymentDate = paymentMono.block().getBody().getPaymentDate();;
        WebClient webClientOfMeter = WebClient.create(Constants.METER_API_GET_BY_ID + user.getMeterId());
        Mono<ResponseEntity<Meter>> meterMono = webClientOfMeter.get().retrieve().toEntity(Meter.class);
        Meter meter = meterMono.block().getBody();
        Bill bill = new Bill(user,
                             meter.getEnergyDebt(),
                            meter.getDebt()*100,
                            lastPaymentDate
                            );
        model.addAttribute("bill",bill);
        model.addAttribute("userId", user.getId());
        model.addAttribute("amount", (int)(meter.getDebt()*100)); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "bill";
    }
}
