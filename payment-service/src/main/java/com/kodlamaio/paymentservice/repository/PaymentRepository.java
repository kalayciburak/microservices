package com.kodlamaio.paymentservice.repository;

import com.kodlamaio.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    boolean existsByCardNumberAndFullNameAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
            String cardNumber, String fullName, int cardExpirationYear, int cardExpirationMonth, String cardCvv);
    Payment findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
}
