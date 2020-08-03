package org.zenith.legion.general.ex;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
