package com.kodlamaio.paymentservice.business.concretes;

import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.abstracts.PosService;
import com.kodlamaio.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.business.rules.PaymentBusinessRules;
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
    private final PaymentBusinessRules rules;

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
        rules.checkIfPaymentExists(id);
        Payment payment = repository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request, CustomerRequest customerRequest) {
        rules.checkIfCardNumberExists(request.getCardNumber());
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(UUID.randomUUID().toString());
        setCustomerInformation(customerRequest, payment);
        repository.save(payment);
        CreatePaymentResponse response = mapper.forResponse().map(payment, CreatePaymentResponse.class);

        return response;
    }

    @Override
    public UpdatePaymentResponse update(UpdatePaymentRequest request, String id, CustomerRequest customerRequest) {
        rules.checkIfPaymentExists(id);
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        setCustomerInformation(customerRequest, payment);
        repository.save(payment);
        UpdatePaymentResponse response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(String id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }

    @Override
    public void processRentalPayment(CreateRentalPaymentRequest request) {
        rules.checkIfPaymentValid(request);
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        double balance = payment.getBalance();
        rules.checkIfBalanceIsEnough(request, balance);
        posService.pay(); // Fake payment
        payment.setBalance(balance - request.getPrice());
        repository.save(payment);
    }

    private void setCustomerInformation(CustomerRequest customerRequest, Payment payment) {
        payment.setCustomerId(customerRequest.getCustomerId());
        payment.setCustomerUserName(customerRequest.getCustomerUserName());
        payment.setCustomerFirstName(customerRequest.getCustomerFirstName());
        payment.setCustomerLastName(customerRequest.getCustomerLastName());
        payment.setCustomerEmail(customerRequest.getCustomerEmail());
    }
}
