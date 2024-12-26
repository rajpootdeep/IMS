package com.deep.ims.controller;

import com.deep.ims.entity.Incident;
import com.deep.ims.entity.User;
import com.deep.ims.exception.CommonException;
import com.deep.ims.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1")
sealed interface IMSControllerApi permits IMSController{

    @PostMapping("user/new")
    ResponseEntity<String> registerUser(@RequestBody User user);

    @PostMapping("incident/new")
    ResponseEntity<String> createIncident(@RequestBody Incident incident) throws CommonException, ResourceNotFoundException;


    @PutMapping("incident")
    ResponseEntity<String> updateIncident(@RequestBody Incident incident) throws ResourceNotFoundException;

    @GetMapping("user/{id}")
    ResponseEntity<User> getuserById(@PathVariable Long id) throws ResourceNotFoundException;

    @GetMapping("incident/{id}")
    ResponseEntity<Incident> getIncidentById(@PathVariable String id) throws CommonException, ResourceNotFoundException;



}
