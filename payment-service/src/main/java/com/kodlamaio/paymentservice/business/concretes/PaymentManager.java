package com.kodlamaio.paymentservice.business.concretes;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.abstracts.PosService;
import com.kodlamaio.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
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
    public void checkIfPaymentSuccessful(CreateRentalPaymentRequest request) {
        checkPayment(request);
    }

    private void checkPayment(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndFullNameAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getFullName(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv())) {
            throw new BusinessException(Messages.Payment.NotAValidPayment);
        } else {
            Payment payment = repository.findByCardNumber(request.getCardNumber());
            double balance = payment.getBalance();
            if (balance < request.getPrice()) {
                throw new BusinessException(Messages.Payment.NotEnoughMoney);
            } else {
                posService.pay(); // Fake payment
                payment.setBalance(balance - request.getPrice());
                repository.save(payment);
            }
        }
    }

    private void checkIfPaymentExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Payment.NotFound);
        }
    }

    private void checkIfCardNumberExists(String cardNumber) {
        if (repository.existsByCardNumber(cardNumber)) {
            throw new BusinessException(Messages.Payment.CardNumberAlreadyExists);
        }
    }
}
