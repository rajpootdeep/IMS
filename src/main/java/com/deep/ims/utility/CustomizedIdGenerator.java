package com.deep.ims.utility;

import com.deep.ims.entity.Incident;
import com.deep.ims.repository.IncidentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class CustomizedIdGenerator {

    @Autowired
    private IncidentRepo incidentRepo;

    public String generateId(Incident incident) {
            int nextInteralPartOfId = getNextIntegralPartOfIdFromDB();
            String generateUniqueId="RMG"+ String.format("%05d",nextInteralPartOfId)+ Year.now().getValue();
        return generateUniqueId;
    }

    private int getNextIntegralPartOfIdFromDB() {
        String lastId = incidentRepo.findTopByOrderByIdDesc().map(Incident::getId).orElse("RMG000002024");
        return Integer.parseInt(lastId.substring(3, 8)) + 1;
    }
}
