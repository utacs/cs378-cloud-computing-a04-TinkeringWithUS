package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.time.format.DateTimeFormatter;

public class GpsTopKRatioMapper extends Mapper<Object, Text, Text, IntWritable> {

    LineValidator validator = new LineValidator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
        String line = value.toString();

        ErrorType errorType = validator.validateLine(line);
        String[] fields = line.split(",");
        String medallion = fields[0];

        if(errorType == ErrorType.GPS_ERROR) {            
            context.write(new Text(medallion), new IntWritable(1));
        } else if (errorType == ErrorType.NO_ERROR) {
            context.write(new Text(medallion), new IntWritable(0));
        }
	}


    public void cleanup(Context context) throws IOException, InterruptedException {
        validator.endValidation();
    }
}
