package com.root.meter.controller;

import com.root.meter.DTO.ChargeRequest;
import com.root.meter.DTO.PaymentDTO;
import com.root.meter.DTO.PaymentView;
import com.root.meter.constants.Constants;
import com.root.meter.model.Payment;
import com.root.meter.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Random;

/**
 * controller that will receive the POST request made by the checkout form and submit
 * the charge to Stripe via our StripeService.
 */
@Controller
@Transactional  //all or not
public class ChargeController {

    @Autowired
    private StripeService stripPaymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = stripPaymentsService.charge(chargeRequest);  //perform charge on strip and get the result
        //generate receipt and reset all the debt(amount$ and energy)
        WebClient webClient = WebClient.create(Constants.PAYMENT_API_SAVE);
        chargeRequest.setResetNo(new Long(new Random().nextInt(20000000)));
        Mono<ResponseEntity<PaymentDTO>> paymentMono = webClient.post()
                .body(Mono.just(chargeRequest), ChargeRequest.class)
                .retrieve().toEntity(PaymentDTO.class);
        //populate to payment to be populated to html
        PaymentDTO payment_receipt = paymentMono.block().getBody();
        model.addAttribute("payment", payment_receipt);
        return "paymentResult";
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<String> handleError(Model model, StripeException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}