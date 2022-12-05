package com.torukobyte.paymentservice.business.concretes;

import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.paymentservice.business.abstracts.PaymentService;
import com.torukobyte.paymentservice.business.abstracts.PosService;
import com.torukobyte.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.requests.get.PaymentRequest;
import com.torukobyte.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import com.torukobyte.paymentservice.entities.Payment;
import com.torukobyte.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapperService mapper;
    private final PosService posService;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = repository.findAll();
        List<GetAllPaymentsResponse> response = payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetPaymentResponse getById(String id) {
        checkIfPaymentExists(id);
        Payment payment = repository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        checkIfCardNumberExists(request.getCardNumber());
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(UUID.randomUUID().toString());
        repository.save(payment);
        CreatePaymentResponse response = mapper.forResponse().map(payment, CreatePaymentResponse.class);

        return response;
    }

    @Override
    public UpdatePaymentResponse update(UpdatePaymentRequest request, String id) {
        checkIfPaymentExists(id);
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);
        UpdatePaymentResponse response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfPaymentExists(id);
        repository.deleteById(id);
    }

    @Override
    public void checkIfPaymentSuccessful(PaymentRequest request) {
        checkPayment(request);
    }

    private void checkPayment(PaymentRequest request) {
        if (!repository.existsByCardNumberAndFullNameAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getFullName(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv())) {
            throw new BusinessException("NOT_A_VALID_PAYMENT!");
        } else {
            double balance = repository.findByCardNumber(request.getCardNumber()).getBalance();
            if (balance < request.getPrice()) {
                throw new BusinessException("NOT_ENOUGH_MONEY!");
            } else {
                posService.pay(); // Fake payment
                Payment payment = repository.findByCardNumber(request.getCardNumber());
                payment.setBalance(balance - request.getPrice());
                repository.save(payment);
            }
        }
    }

    private void checkIfPaymentExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("PAYMENT_NOT_FOUND!");
        }
    }

    private void checkIfCardNumberExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new BusinessException("CARD_NUMBER_ALREADY_EXISTS!");
        }
    }
}
