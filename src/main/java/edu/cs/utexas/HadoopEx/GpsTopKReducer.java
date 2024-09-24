package edu.cs.utexas.HadoopEx;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.PriorityQueue;

public class GpsTopKReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
    
    private PriorityQueue<WordAndCount> pq = new PriorityQueue<WordAndCount>(10);;

    private Logger logger = Logger.getLogger(GpsTopKReducer.class);
    
    public void reduce(Text key, Iterable<FloatWritable> values, Context context)
            throws java.io.IOException, InterruptedException {

        for (FloatWritable value : values) {
            logger.info("GpsTopKReducer Text: Add this item  " + value);
    
            pq.add(new WordAndCount(new Text(key), new FloatWritable(value.get())));
    
            logger.info("Reducer Text: " + key.toString() + " , Count: " + value.toString());
            logger.info("PQ Status: " + pq.toString());
        }
    
        // keep the priorityQueue size <= heapSize
        while (pq.size() > 5) {
            pq.poll();
        }
    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("GpsTopKReducer cleanup cleanup.");
        logger.info("pq.size() is " + pq.size());

        for (WordAndCount value : pq) {
            context.write(value.getWord(), value.getCount());
            logger.info("TopKReducer - Top-10 are:  " + value.getWord() + "  Count:"+ value.getCount());
        }

    }

}
