package com.lacsmx.chargingsessionstore.utils;

import java.time.LocalDateTime;

/**
 * This class will provide common operations
 *
 * @author CS Luis Date: Jan 18, 2020
 */
public class Utils {
    /**
     * Function to determine if a time is within a period from start and end
     * @param current
     * @param start
     * @param end
     * @return
     */
    public static  boolean isInTimeWindow(LocalDateTime current, LocalDateTime start, LocalDateTime end) {
        //return current.isAfter(start) && current.isBefore(end);
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0 ;
    }
}
