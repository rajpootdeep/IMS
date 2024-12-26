package com.deep.ims.service;

import com.deep.ims.entity.Incident;
import com.deep.ims.entity.User;
import com.deep.ims.exception.CommonException;
import com.deep.ims.exception.ResourceNotFoundException;
import com.deep.ims.repository.IncidentRepo;
import com.deep.ims.utility.CustomizedIdGenerator;
import com.deep.ims.utility.MappingUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.deep.ims.utility.MappingUtility.priority;
import static com.deep.ims.utility.MappingUtility.type;

@Service
public class IncidentService {

    private static final Logger log = LoggerFactory.getLogger(IncidentService.class);
    @Autowired
    private IncidentRepo incidentRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomizedIdGenerator customizedIdGenerator;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public ResponseEntity<String> saveIncident(Incident incident, Long userId) throws ResourceNotFoundException, CommonException {

        if(priority.contains(incident.getPriority()) && MappingUtility.status.contains(incident.getStatus()) && type.contains(incident.getType())){
            ResponseEntity<User> user=userService.getUserById(userId);
            if (200 != user.getStatusCode().value()) {
                throw new CommonException("May user not exist or went wrong");
            }
            incident.setCreatedByUser(user.getBody());
            incident.setId(customizedIdGenerator.generateId(incident));
            incidentRepo.save(incident);
            return  ResponseEntity.status(HttpStatus.CREATED).body("Incident created : "+incident.getId());
        }else {
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Incident> getIncidentById(String id,Long userId) throws ResourceNotFoundException, CommonException {
        log.info("getIncidentById method invoked");
        Optional<Incident> incident=Optional.empty();
        ResponseEntity<User> user=userService.getUserById(userId);
        if (200 != user.getStatusCode().value()) {
            throw new CommonException("May user not exist or something went wrong");
        }
        if(Objects.requireNonNull(user.getBody()).getRole().equalsIgnoreCase("Admin")){
            incident=incidentRepo.findById(id);
        }else {
            incident=incidentRepo.findByIdAndCreatedBy(id,user.getBody());
        }
        if(incident.isPresent()){
            log.info("getIncidentById method completed");
            return new ResponseEntity<>(incident.get(),HttpStatus.FOUND);
        }
        throw new ResourceNotFoundException("Resource not found with incident id : "+id);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public ResponseEntity<String> updateIncident(Incident incident,Long userId) throws ResourceNotFoundException {
        Optional<Incident> inc=incidentRepo.findById(incident.getId());
        if (!inc.isPresent() || null== incident.getCreatedBy() || null==incident.getCreatedBy().getId() || incident.getCreatedBy().getId() != inc.get().getCreatedBy().getId()){
            throw new ResourceNotFoundException("Resource not exist for given incident ID : "+incident.getId() +" or User is invalid : ");
        } else if (inc.get().getStatus().equalsIgnoreCase("Closed")) {
            return new ResponseEntity<>("Incident is already closed can't modified : "+inc.get().getId(),HttpStatus.CONFLICT);
        }else if(!Objects.equals(userId, incident.getCreatedBy().getId()) && !inc.get().getCreatedBy().getRole().equalsIgnoreCase("Admin")) {

            return new ResponseEntity<>("Authenticated user is not allowed to modify this incident ",HttpStatus.FORBIDDEN);
        }else {
            incident.setCreatedByUser(inc.get().getCreatedBy());
            incidentRepo.save(incident);
            return new ResponseEntity<>("Incident updated successfully",HttpStatus.OK);
        }

    }

}
