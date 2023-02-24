package com.kodlamaio.paymentservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Payment.NotFound);
        }
    }

    public void checkIfCardNumberExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new BusinessException(Messages.Payment.CardNumberAlreadyExists);
        }
    }

    public void checkIfPaymentValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardholderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardholder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv())) {
            throw new BusinessException(Messages.Payment.NotAValidPayment);
        }
    }

    public void checkIfBalanceIsEnough(CreateRentalPaymentRequest request, double balance) {
        if (balance < request.getPrice()) {
            throw new BusinessException(Messages.Payment.NotEnoughMoney);
        }
    }
}
