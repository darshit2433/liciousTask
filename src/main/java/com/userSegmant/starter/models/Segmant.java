package com.userSegmant.starter.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segmant {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String segmantName;
    private List<SegmantRuleSet> segmantRuleSets;


}
