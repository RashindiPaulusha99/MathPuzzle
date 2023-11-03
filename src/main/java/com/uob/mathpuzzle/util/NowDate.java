package com.uob.mathpuzzle.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class NowDate {

    public Date getNowDateTime(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDateTime = dateFormat.format(now);

        Date formattedDate = null;
        try {
           formattedDate = dateFormat.parse(formattedDateTime);
        } catch (Exception e) {
            log.error("Error at now date: " + e.getMessage());
        }

        return formattedDate;

    }
}
