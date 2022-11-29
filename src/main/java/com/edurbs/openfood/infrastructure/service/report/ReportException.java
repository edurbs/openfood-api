package com.edurbs.openfood.infrastructure.service.report;

public class ReportException  extends RuntimeException {

    public ReportException(Throwable cause) {
        super(cause);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
