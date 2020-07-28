package org.zenith.legion.sysadmin.ex;

public class InvalidBatchJobException extends RuntimeException {

    public InvalidBatchJobException(){}

    public InvalidBatchJobException(String msg) {
        super(msg);
    }
}
