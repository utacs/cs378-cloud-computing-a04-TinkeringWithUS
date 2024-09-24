package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class GpsTopKRatioReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
    public void reduce(Text medallion, Iterable<IntWritable> isErrors, Context context)
            throws java.io.IOException, InterruptedException {

        HashMap<Integer, Integer> counts = new HashMap<>();
        for (IntWritable isError : isErrors) {
            counts.merge(isError.get(), 1, Integer::sum);
        }
        
        Float ratio = (float) counts.getOrDefault(1, 0)/ (float) (counts.getOrDefault(0, 0) + counts.getOrDefault(1, 0));
        context.write(medallion, new FloatWritable(ratio));
    }
}
