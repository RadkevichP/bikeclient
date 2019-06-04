package com.elinext.bikeclient.client;

import com.elinext.bikeclient.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "BikeClient", url = "http://localhost:8097/api/v1")
public interface BikeClient {

    @GetMapping("/bikes")
    List<Bike> findAll();
}
