package com.kodlamaio.paymentservice.adapters;

import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.paymentservice.business.abstracts.PosService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        int randomNumber = new Random().nextInt(2);
        if (randomNumber == 1) {
            throw new BusinessException("PAYMENT_FAILED");
        }
    }
}