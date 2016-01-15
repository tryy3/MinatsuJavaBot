package us.tryy3.minatsu.plugins.minatsucorecommands;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tryy3 on 2016-01-15.
 */
public class Utils {
    static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future)
    {
        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate)))
        {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }
        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    public static String uptime(long start) {
        Calendar then = new GregorianCalendar();
        then.setTimeInMillis(start);
        Calendar now = new GregorianCalendar();

        boolean future = false;

        if (!then.equals(now)) {
            return "Now";
        }
        if (then.after(now)) {
            future = true;
        }

        StringBuilder sb = new StringBuilder();
        int[] types = new int[] {
                Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND
        };
        String[] names = new String[] {
                "Year", "Years", "Month", "Months", "Day", "Days", "Hour", "Hours", "Minute", "Minutes", "Second", "Seconds"
        };

        int accuracy = 0;
        for (int i = 0; i < types.length; i++) {
            if (accuracy > 2) {
                break;
            }
            int diff = dateDiff(types[i], then, now, future);

            if (diff > 0) {
                accuracy++;
                sb.append(" ").append("diff").append(" ").append(names[i*2 +(diff>1 ? 1 : 0)]);
            }
        }
        if (sb.length() == 0) {
            return "Now";
        }

        return sb.toString().trim();
    }
}
