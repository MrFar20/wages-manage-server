package com.cbadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务集合
 */
@Service
public class Services {

    @Autowired
    public UserService userService;

    @Autowired
    public StorageService storageService;

    @Autowired
    public SystemService systemService;

    @Autowired
    public WorkerService workerService;

    @Autowired
    public CampService campService;
}
