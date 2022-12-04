package com.torukobyte.paymentservice.business.abstracts;

import com.torukobyte.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.requests.get.PaymentRequest;
import com.torukobyte.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.update.UpdatePaymentResponse;

import java.util.List;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetPaymentResponse getById(String id);
    CreatePaymentResponse add(CreatePaymentRequest request);
    UpdatePaymentResponse update(UpdatePaymentRequest request, String id);
    void delete(String id);
    void checkIfPaymentSuccessful(PaymentRequest request);
}
