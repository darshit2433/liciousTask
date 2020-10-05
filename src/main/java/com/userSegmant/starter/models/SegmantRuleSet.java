package com.userSegmant.starter.models;

import com.userSegmant.starter.Enums.Oprators;
import lombok.Data;

import java.util.List;

@Data
public class SegmantRuleSet {

    private String key;
    private Oprators oprator;
    private String  value;
    private List<SegmantRuleSet> oprations;



}

