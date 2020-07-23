package org.zenith.legion.common.base;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseDocument implements Serializable, Cloneable {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
