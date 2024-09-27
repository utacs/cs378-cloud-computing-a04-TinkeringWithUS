package edu.cs.utexas.HadoopEx;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class MainDriver {
    public static void main(String[] args) throws Exception {
        // Run all 3 map reduce tasks all at once
        /**
         * To chain map reduce jobs, have the first map reduce write to
         * a file for the 2nd map reduce job to read from
         * 
         * Notes
         * 1. store a bunch of (hour of day, number of error) tuples in file
         * 2. top 5 highest taxis with highest gps error rates (taxi, percent of errors)
         * 3. top 10 most efficient drivers in earnings / minute
         */

        // flags
        final String REDUCE_EARNINGS = "e";
        final String REDUCE_GPS = "g";
        final String REDUCE_GPS_RATIO = "r";

        String jobsToRun = args[0];
        String inputFile = args[1];

        int results = 0;

        if (jobsToRun.contains(REDUCE_EARNINGS)) {
            String[] earningArgs = new String[] { inputFile, args[3] };

            int tempResult = ToolRunner.run(new Configuration(), new EarningDriver(), earningArgs);

            System.out.println("temp result of running reduce earnings: " + tempResult);

            if(tempResult != 0) {
                System.out.println("Failed to run Earnings reducer");
            }

            results += tempResult; 
        }

        if (jobsToRun.contains(REDUCE_GPS)) {
            String[] gpsArgs = new String[] { inputFile, args[2] };

            int tempResult = ToolRunner.run(new Configuration(), new GpsDriver(), gpsArgs);

            System.out.println("temp result of running reduce gps errors: " + tempResult);

            if(tempResult != 0) {
                System.out.println("Failed to run Gps reducer");
            }

            results += tempResult; 
        }

        if (jobsToRun.contains(REDUCE_GPS_RATIO)) {
            String[] gpsRatioArgs = new String[] { inputFile, args[4], args[5] };

            int tempResult = ToolRunner.run(new Configuration(), new GpsTopKDriver(), gpsRatioArgs);

            System.out.println("temp result of running reduce gps RATIO errors: " + tempResult);

            if(tempResult != 0) {
                System.out.println("Failed to run Gps reducer");
            }

            results += tempResult; 
        }

        System.exit(results);
    }
}
