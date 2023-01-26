package com.kodlamaio.paymentservice.business.abstracts;

import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.update.UpdatePaymentResponse;

import java.util.List;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetPaymentResponse getById(String id);
    CreatePaymentResponse add(CreatePaymentRequest request, CustomerRequest customerRequest);
    UpdatePaymentResponse update(UpdatePaymentRequest request, String id, CustomerRequest customerRequest);
    void delete(String id);
    void checkIfPaymentSuccessful(CreateRentalPaymentRequest request);
}
