package com.root.meter.service;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.constants.Constants;
import com.root.meter.model.Payment;
import com.root.meter.model.Users;
import com.root.meter.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    public Payment save(ChargeRequest chargeRequest){
        String amount = chargeRequest.getAmount();
        WebClient webClient = WebClient.create(Constants.USER_API_FIND_BY_ID +chargeRequest.getUserId());
        Mono<ResponseEntity<Users>> res = webClient.get().retrieve().toEntity(Users.class);
        Users user = res.block().getBody();

        Payment payment = new Payment(new Double(amount), user, LocalDate.now());
        //reset debt of meter if success
        user.getMeter().setDebt(0.0);
        user.getMeter().setEnergyDebt(0.0);
        return paymentRepo.save(payment);
    }
    public Payment get(Long userId){
        return paymentRepo.findFirstPaymentByUsersIdOrderByPaymentDateDesc(userId);
    }
}
