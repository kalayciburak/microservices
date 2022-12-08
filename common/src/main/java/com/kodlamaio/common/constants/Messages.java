package com.kodlamaio.common.constants;

public class Messages {
    public static class Car {
        public static final String NotExists = "CAR.NOT_EXISTS";
        public static final String Exists = "CAR.ALREADY_EXISTS";
        public static final String NotAvailable = "CAR.NOT_AVAILABLE";
    }

    public static class Model {
        public static final String NotExists = "MODEL.NOT_EXISTS";
        public static final String Exists = "MODEL.ALREADY_EXISTS";
    }

    public static class Brand {
        public static final String NotExists = "BRAND.NOT_EXISTS";
        public static final String Exists = "BRAND.ALREADY_EXISTS";
    }

    public static class Filter {
        public static final String NotExists = "CAR_NOT_EXISTS";
    }

    public static class Rental {
        public static final String NotFound = "RENTAL.NOT_FOUND";
        public static final String Created = "RENTAL.CREATED";
        public static final String Updated = "RENTAL.UPDATED";
        public static final String Deleted = "RENTAL.DELETED";
    }

    public static class Payment {
        public static final String NotFound = "PAYMENT.NOT_FOUND";
        public static final String CardNumberAlreadyExists = "CARD_NUMBER_ALREADY_EXISTS";
        public static final String NotEnoughMoney = "NOT_ENOUGH_MONEY";
        public static final String NotAValidPayment = "NOT_A_VALID_PAYMENT";
        public static final String Failed = "PAYMENT.FAILED";
    }

    public static class Invoice {
        public static final String NotFound = "INVOICE.NOT_FOUND";
    }

    public static class Exception {
        public static final String Validation = "VALIDATION_EXCEPTION";
        public static final String Business = "BUSINESS_EXCEPTION";
        public static final String DataIntegrityViolation = "DATA_INTEGRITY_VIOLATION_EXCEPTION";
        public static final String Runtime = "RUNTIME_EXCEPTION";
        public static final String Feign = "FEIGN_EXCEPTION";
    }
}
