package com.torukobyte.rentalservice.business.abstracts;

import com.torukobyte.rentalservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.torukobyte.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.torukobyte.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.update.UpdateRentalResponse;

import java.util.List;

public interface RentalService {
    List<GetAllRentalsResponse> getAll();
    GetRentalResponse getById(String id);
    CreateRentalResponse add(CreateRentalRequest request, CreatePaymentRequest paymentRequest);
    UpdateRentalResponse update(UpdateRentalRequest request, String id);
    void delete(String id);
}
