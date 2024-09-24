package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

public class GpsTopKMapper extends Mapper<Text, FloatWritable, Text, FloatWritable> {
	private Logger logger = Logger.getLogger(GpsTopKMapper.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

    private PriorityQueue<WordAndCount> pq;

	public void setup(Context context) {
		pq = new PriorityQueue<>();
        // TODO, call this method
	}

	public void map(Text key, FloatWritable value, Context context) 
			throws IOException, InterruptedException {
        pq.add(new WordAndCount(new Text(key), new FloatWritable(value.get())) );
        if (pq.size() > 5) {
            pq.poll();
        }
	}


    public void cleanup(Context context) throws IOException, InterruptedException {
        while (pq.size() > 0) {
			WordAndCount wordAndCount = pq.poll();
			context.write(wordAndCount.getWord(), wordAndCount.getCount());
			logger.info("GpsTopKMapper PQ Status: " + pq.toString());
		}
    }
}
