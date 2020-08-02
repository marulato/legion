package org.zenith.legion.common.base;

import java.io.Serializable;
import java.util.Date;

public class AjaxResponseBody implements Serializable {

    private int code;
    private Object data;
    private Date requestAt;
    private Date respondAt;

    AjaxResponseBody(){}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    void setData(Object data) {
        this.data = data;
    }

    public Date getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    public Date getRespondAt() {
        return respondAt;
    }

    public void setRespondAt(Date respondAt) {
        this.respondAt = respondAt;
    }
}
