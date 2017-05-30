package com.infoDiscover.common.dimension.time;

import com.infoDiscover.common.dimension.time.constants.TimeDimensionConstants;
import com.infoDiscover.common.dimension.time.dimension.DayDimensionVO;
import com.infoDiscover.common.util.DateUtil;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by sun.
 */
public class DayDimensionManager {

    public static DayDimensionVO getDayDimensionVO(String factTypePrefix, Date date) {
        DateTime dateTime = DateUtil.getDateTime(date.getTime());

        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DayDimensionVO dayDimension = new DayDimensionVO(factTypePrefix +
                TimeDimensionConstants.DAY, year, month, day);
        return dayDimension;
    }

}
