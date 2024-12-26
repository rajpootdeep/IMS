package com.deep.ims.controller;

import com.deep.ims.entity.Incident;
import com.deep.ims.entity.User;
import com.deep.ims.exception.CommonException;
import com.deep.ims.exception.ResourceNotFoundException;
import com.deep.ims.security.SecurityUtils;
import com.deep.ims.service.IncidentService;
import com.deep.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
@RestController
final class IMSController implements IMSControllerApi {



    private static final Logger log = LoggerFactory.getLogger(IMSController.class);
    @Autowired
    private UserService userService;

    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    private IncidentService incidentService;

    @Override
    public ResponseEntity<String> registerUser(User user) {
        log.info("registerUser controller method invoked");
        return userService.saveUser(user);
    }

    @Override
    public ResponseEntity<String> createIncident(Incident incident) throws CommonException, ResourceNotFoundException {
        log.info("createIncident controller method invoked");
        Long userId=securityUtils.getAuthenticatedUserId();
        return incidentService.saveIncident(incident, userId);
    }

    @Override
    public ResponseEntity<String> updateIncident(Incident incident) throws ResourceNotFoundException {
        log.info("updateIncident controller method invoked");
        Long userId=securityUtils.getAuthenticatedUserId();
        return incidentService.updateIncident(incident, userId);

    }

    @Override
    public ResponseEntity<User> getuserById(Long id) throws ResourceNotFoundException {
        log.info("controller method getuserById invoked");
        return userService.getUserById(id);
    }

    @Override
    public ResponseEntity<Incident> getIncidentById(String id) throws CommonException, ResourceNotFoundException {
        log.info("getIncidentById controller method invoked");
        Long userId=securityUtils.getAuthenticatedUserId();
        return incidentService.getIncidentById(id, userId);
    }
}
