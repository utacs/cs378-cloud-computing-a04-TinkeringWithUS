package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import java.util.HashMap;

public class GpsReducer extends Reducer<IntWritable, LongWritable, IntWritable, LongWritable> {
    HashMap<Integer, Long> hourToNumErrors = new HashMap<>();

    public void reduce(IntWritable hour, Iterable<LongWritable> errors, Context context)
            throws java.io.IOException, InterruptedException {

        for (LongWritable numErrors : errors) {
            if (hourToNumErrors.containsKey(hour.get())) {
                long prevNumErrors = hourToNumErrors.get(hour.get());

                hourToNumErrors.put(hour.get(), prevNumErrors + numErrors.get());
            } else {
                hourToNumErrors.put(hour.get(), numErrors.get());
            }
        }

    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        for (int hour : hourToNumErrors.keySet()) {
            context.write(new IntWritable(hour), new LongWritable(hourToNumErrors.get(hour)));
        }
    }

}
