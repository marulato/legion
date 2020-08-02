package org.zenith.legion.common.base;

import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.common.cache.ICache;
import org.zenith.legion.common.cache.MasterCodeCache;
import org.zenith.legion.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjaxResponseBuilder {

    private final AjaxResponseBody ajaxResponseBody;
    private final List<String> errorCodes;
    private final List<Object> dataObjects;

    private AjaxResponseBuilder(int code){
        ajaxResponseBody = new AjaxResponseBody();
        ajaxResponseBody.setCode(code);
        errorCodes = new ArrayList<>();
        dataObjects = new ArrayList<>();
    }

    public static AjaxResponseBuilder build(int responseCode) {
        return new AjaxResponseBuilder(responseCode);
    }

    public void addError(String errorCode, String field) {
        if (StringUtils.isNotBlank(errorCode)) {
            errorCodes.add(errorCode);
        }
    }
    public void addErrors(List<String> errorCode) {
        if (errorCode != null) {
            errorCodes.addAll(errorCode);
        }
    }

    public void addDataObject(Object object) {
        if (object != null && errorCodes.isEmpty()) {
            dataObjects.add(object);
        }
    }

    public void addDataObjects(List<Object> objects) {
        if (objects != null && errorCodes.isEmpty()) {
            dataObjects.addAll(objects);
        }
    }

    public AjaxResponseBody respond() {
        ajaxResponseBody.setRespondAt(new Date());
        if (!errorCodes.isEmpty()) {
            ICache<String, MasterCode> masterCodeCache = CachePool.getCache(MasterCodeCache.KEY, MasterCodeCache.class);
            List<MasterCode> data = new ArrayList<>();
            for (String errorCode : errorCodes) {
                MasterCode masterCode = masterCodeCache.get(errorCode);
                if (masterCode != null) {
                    data.add(masterCode);
                }
            }
            ajaxResponseBody.setData(data);
        } else {
            ajaxResponseBody.setData(dataObjects);
        }
        return ajaxResponseBody;
    }


}
