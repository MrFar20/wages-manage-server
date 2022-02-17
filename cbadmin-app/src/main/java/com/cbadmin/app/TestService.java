package com.cbadmin.app;

import com.cbadmin.dao.entity.Worker;
import com.cbadmin.dao.repo.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    private WorkerRepo workerRepo;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWorker(Worker worker) {
        this.workerRepo.save(worker);
    }

}
