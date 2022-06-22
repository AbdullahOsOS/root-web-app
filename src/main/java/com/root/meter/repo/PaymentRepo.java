package com.root.meter.repo;

import com.root.meter.DTO.PaymentView;
import com.root.meter.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PaymentRepo extends CrudRepository<Payment,Long> {

    PaymentView findFirstPaymentByUsersIdOrderByPaymentDateDesc(Long userId);        //findFirstByOrderBySeatNumberAsc
}
