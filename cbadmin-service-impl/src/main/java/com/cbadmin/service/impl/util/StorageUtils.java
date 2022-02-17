package com.cbadmin.service.impl.util;

import com.cbadmin.common.constant.Keys;
import com.cbadmin.common.systemconfig.LocalStorageAccessUrl;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.storage.client.config.StorageProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageUtils implements InitializingBean {

    private static LocalStorageAccessUrl localStorageAccessUrl;

    private static StorageProperties storageProperties;

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();




    /**
     * 获取访问的url
     * @param s
     * @return
     */
    public static final String setAccessUrl(StorageV s) {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(localStorageAccessUrl.get());
        }
        if (Keys.STORAGE_TYPE_LOCAL.equals(s.getType())) {
            s.setFileUrl(s.getFileUrl().replace(storageProperties.getLocal().getRoot(), THREAD_LOCAL.get()));
        }
        return s.getFileUrl();
    }

    /**
     * clear
     */
    public static final void clear() {
        THREAD_LOCAL.remove();
    }


    @Autowired
    private LocalStorageAccessUrl _localStorageAccessUrl;

    @Autowired
    private  StorageProperties _storageProperties;
    @Override
    public void afterPropertiesSet() throws Exception {
        storageProperties = _storageProperties;
        localStorageAccessUrl = _localStorageAccessUrl;
    }
}
