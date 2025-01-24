package com.rainer.veebipood.service;

import com.rainer.veebipood.models.OmnivaPM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ParcelMachineService {

    @Autowired
    RestTemplate restTemplate;

    public String getElectricityPrices() {
        String url = "https://dashboard.elering.ee/api/nps/price";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public List<OmnivaPM> getParcelMachines(String country) {
        String url = "https://www.omniva.ee/locations.json";

        ResponseEntity<OmnivaPM[]> response = restTemplate.exchange(url, HttpMethod.GET, null, OmnivaPM[].class);

        List<OmnivaPM> omnivaPMs = List.of(response.getBody());
        omnivaPMs = omnivaPMs.stream().filter(e -> e.getA0_NAME().equals(country.toLowerCase())).toList();
        return omnivaPMs;
    }
}
