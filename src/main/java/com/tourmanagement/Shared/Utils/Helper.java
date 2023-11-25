package com.tourmanagement.Shared.Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helper {
    public static List<Date> getNearestDates(int numberOfDate) {
        List<Date> result = new ArrayList<>();

        for (int i = 0; i < numberOfDate; i++) {
            LocalDate previousDate = LocalDate.now().minusDays(i);
            result.add(Date.from(previousDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return result;
    }
}