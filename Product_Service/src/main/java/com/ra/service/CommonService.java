package com.ra.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class CommonService {


    public Pageable pagination(String order,Integer page,Integer limit,String sort){
        Pageable pageable;
        if(order.equals("asc")){
            pageable= PageRequest.of(page,limit, Sort.by(sort).ascending());
        }else {
            pageable= PageRequest.of(page,limit,Sort.by(sort).descending());
        }
        return pageable;
    }


    public boolean isDateTooOld(Date givenDate) {
        // Get the current date
        Date currentDate = new Date();

        // Calculate the difference in milliseconds between the given date and the current date
        long millisecondsDifference = currentDate.getTime() - givenDate.getTime();

        // Convert the milliseconds to days
        long daysDifference = millisecondsDifference / (24 * 60 * 60 * 1000);

        // Check if the difference is greater than 30 days (approximately one month)
        return daysDifference > 30;
    }



}
