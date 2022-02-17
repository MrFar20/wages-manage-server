package com.cbadmin.service.impl.converter;

import com.cbadmin.dao.entity.Storage;
import com.cbadmin.model.vo.StorageV;
import com.cbmai.core.converter.BaseObjectConverter;
import org.springframework.stereotype.Component;

@Component
public class Storage2StorageVConverter extends BaseObjectConverter<Storage, StorageV> {

    @Override
    public StorageV fromEntity(Storage entity) {
        StorageV s = super.fromEntity(entity);
        return s;
    }
}
