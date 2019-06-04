package com.elinext.bikeclient.service;

import com.elinext.bikeclient.client.BikeClient;
import com.elinext.bikeclient.model.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceFeign implements BikeService {

    private BikeClient bikeClient;

    @Autowired
    public BikeServiceFeign(BikeClient bikeClient) {
        this.bikeClient = bikeClient;
    }

    @Override
    public List<Bike> findAll() {
        return bikeClient.findAll();
    }
}
