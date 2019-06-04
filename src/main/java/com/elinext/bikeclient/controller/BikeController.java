package com.elinext.bikeclient.controller;

import com.elinext.bikeclient.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feign")
public class BikeController {

    private BikeService bikeService;

    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @RequestMapping("/bikes")
    public String viewAll(Model model) {
        model.addAttribute("bikes", bikeService.findAll());
        return "bikes";
    }
}
