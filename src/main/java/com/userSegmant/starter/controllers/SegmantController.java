package com.userSegmant.starter.controllers;

import com.userSegmant.starter.Manager.SegmantManger;
import com.userSegmant.starter.models.Segmant;
import com.userSegmant.starter.models.User;
import com.userSegmant.starter.repositories.SegmantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SegmantController {



    @Autowired
    private SegmantManger segmantManger;

    @PostMapping("addUpdateSegment")
    @ResponseStatus(HttpStatus.CREATED)
    public Segmant addUpdateSegment(@RequestBody Segmant segmant) {
        return segmantManger.addUpdateSegment(segmant);
    }

    @PostMapping("getUserGroup")
    public List<String> getUserGroup(@RequestBody User user) {
        return segmantManger.getUserGroup(user);
    }

}
