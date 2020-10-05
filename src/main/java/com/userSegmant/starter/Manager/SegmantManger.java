package com.userSegmant.starter.Manager;

import com.google.gson.Gson;
import com.userSegmant.starter.Enums.Oprators;
import com.userSegmant.starter.models.Segmant;
import com.userSegmant.starter.models.SegmantRuleSet;
import com.userSegmant.starter.models.User;
import com.userSegmant.starter.repositories.SegmantDAO;
import com.userSegmant.starter.repositories.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class SegmantManger {
    private final static Logger logger = LoggerFactory.getLogger(SegmantManger.class);
    private Gson gson = new Gson();
    @Autowired
    private SegmantDAO segmantDAO;
    @Autowired
    private UserDAO userDAO;

    public Segmant addUpdateSegment( Segmant segmant) {

        return segmantDAO.save(segmant);
    }



    public List<String> getUserGroup(User user) {
        logger.info("    user Value  " + user.toString());
        List<Segmant> segmantList = segmantDAO.findAll();
        List<String> selectedSegmant = new ArrayList<>();
        for (Segmant segmant : segmantList) {
            boolean isSegmantSelected;

            try {
                if (isSegmentAvailable(user, segmant.getSegmantRuleSets(), Oprators.AND)) {
                    selectedSegmant.add(segmant.getSegmantName());
                }
            } catch (Exception ex) {

                logger.error("Error with segment " + segmant.getSegmantName() + "    " + ex.getMessage());
            }
        }

        user.setSegmentList(selectedSegmant);
        userDAO.save(user);

        return selectedSegmant;
    }

    public boolean isSegmentAvailable(User user, List<SegmantRuleSet> segmantRuleSets, Oprators oprator) throws NoSuchFieldException, IllegalAccessException {
        Boolean isSegmentAvailable = null;

        for (SegmantRuleSet segmantRuleSet : segmantRuleSets) {


            Object userValue = null;
            Object segmentValue = null;
            boolean isSetTrue = false;

            if (!StringUtils.isEmpty(segmantRuleSet.getValue()) && !StringUtils.isEmpty(segmantRuleSet.getKey())) {
                userValue = user.getClass().getField(segmantRuleSet.getKey()).get(user);
                segmentValue = gson.fromJson(gson.toJson(segmantRuleSet.getValue()), userValue.getClass());

            }


            switch (segmantRuleSet.getOprator()) {
                case OR:
                case AND:
                    isSetTrue = isSegmentAvailable(user, segmantRuleSet.getOprations(), segmantRuleSet.getOprator());

                    break;
                case EQ:
                    if (userValue.equals(segmentValue)) {
                        isSetTrue = true;
                    }
                    break;
                case NEQ:
                    if (userValue.equals(segmentValue)) {
                        isSetTrue = true;
                    }
                    break;
                case GT:

                    if (Double.parseDouble(userValue.toString()) > Double.parseDouble(segmentValue.toString())) {
                        isSetTrue = true;
                    }
                    break;
                case LT:

                    if (Double.parseDouble(userValue.toString()) < Double.parseDouble(segmentValue.toString())) {
                        isSetTrue = true;
                    }
                    break;

            }
            if (Objects.isNull(isSegmentAvailable)) {
                isSegmentAvailable = isSetTrue;
            } else {
                if (Oprators.OR.equals(oprator)) {
                    isSegmentAvailable = isSegmentAvailable || isSetTrue;
                } else {
                    isSegmentAvailable = isSegmentAvailable && isSetTrue;
                }

            }

        }

        return isSegmentAvailable;
    }

}







