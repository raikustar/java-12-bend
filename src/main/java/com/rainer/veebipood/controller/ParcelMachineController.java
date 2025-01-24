package com.rainer.veebipood.controller;


import com.rainer.veebipood.models.OmnivaPM;
import com.rainer.veebipood.service.ParcelMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParcelMachineController {

    @Autowired
    private ParcelMachineService parcelMachineService;

    @GetMapping("electricity-prices")
    public String getElectricityPrices() {
        return parcelMachineService.getElectricityPrices();
    }

    @GetMapping("parcel-machines")
    public List<OmnivaPM> getParcelMachines(@RequestParam String country) {
        return parcelMachineService.getParcelMachines(country);
    }
}
