package edu.cs.utexas.HadoopEx;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
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

        int results = 0;

        String[] runnerArgs = Arrays.copyOfRange(args, 1, args.length);

        if (args[0].equals(REDUCE_EARNINGS)) {
            int tempResult = ToolRunner.run(new Configuration(), new EarningDriver(), runnerArgs);

            if (tempResult <= 0) {
                System.out.println("Failed to run Map Reduce for Earnings per minute");
            } else {
                results += tempResult;
            }
        }

        if (args[0].equals(REDUCE_GPS)) {

        }

        if (args[0].equals(REDUCE_GPS_RATIO)) {

        }

        System.exit(results);
    }
}
