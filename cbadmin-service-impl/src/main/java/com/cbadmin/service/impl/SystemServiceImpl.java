package com.cbadmin.service.impl;

import com.cbadmin.common.constant.enums.ExportType;
import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.common.systemconfig.SystemConfigs;
import com.cbadmin.model.LabelValue;
import com.cbadmin.model.SystemConfig;
import com.cbadmin.model.param.user.AddUser;
import com.cbadmin.service.SystemService;
import com.cbadmin.service.UserService;
import com.cbmai.common.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SystemServiceImpl implements SystemService {

    @Value("${system.init.username:useradmin}")
    private String adminUsername;
    @Value("${system.init.pwd:admin123456}")
    private String adminPwd;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfigs systemConfigs;

    @Override
    public void init() {
        AddUser addUser = new AddUser();
        addUser.setUsername(adminUsername);
        addUser.setPwd(Md5Util.md5(adminPwd));
        addUser.setUserType(UserType.ADMIN.getValue());
        userService.addUser(addUser);
    }

    @Override
    public SystemConfig systemConfig() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setSideBarTitle(systemConfigs.sideBarTitle.get());
        systemConfig.setSystemName(systemConfigs.systemName.get());
        systemConfig.setSystemLoginName(systemConfigs.systemLoginName.get());
        systemConfig.setExportType(exportType());
        return systemConfig;
    }

    public List<LabelValue<String>> exportType() {
        return Stream.of(ExportType.values()).collect(ArrayList::new, (array, value) -> array.add(new LabelValue<>(value.getDesc(), value.getType())), (l, r) -> l.addAll(r));
    }
}
