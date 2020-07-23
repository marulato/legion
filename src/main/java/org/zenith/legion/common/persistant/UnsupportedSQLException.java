package org.zenith.legion.common.persistant;

public class UnsupportedSQLException extends RuntimeException {

    public UnsupportedSQLException() {
        super();
    }

    public UnsupportedSQLException(String msg) {
        super(msg);
    }
}
