package org.zenith.legion.sysadmin.ex;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
