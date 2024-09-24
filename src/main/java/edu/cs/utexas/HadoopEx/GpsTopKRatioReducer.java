package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class GpsTopKRatioReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
    HashMap<String, Float> medallionToRatio = new HashMap<>();

    public void reduce(Text medallion, Iterable<IntWritable> isErrors, Context context)
            throws java.io.IOException, InterruptedException {

        HashMap<Integer, Integer> counts = new HashMap<>();
        for (IntWritable isError : isErrors) {
            counts.merge(isError.get(), 1, Integer::sum);
        }
        Float ratio = (float) counts.get(1)/ (float) (counts.get(0) + counts.get(1));
        medallionToRatio.put(medallion.toString(), ratio);

    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        for (String medallion : medallionToRatio.keySet()) {
            context.write(new Text(medallion), new FloatWritable(medallionToRatio.get(medallion)));
        }
    }

}
