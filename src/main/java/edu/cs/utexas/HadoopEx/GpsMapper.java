package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.time.format.DateTimeFormatter;

public class GpsMapper extends Mapper<Object, Text, IntWritable, LongWritable> {

    LineValidator validator = new LineValidator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
        String line = value.toString();

        ErrorType errorType = validator.validateLine(line);

        if(errorType == ErrorType.GPS_ERROR) {
            String[] fields = line.split(",");

            String pickupTime = fields[2];
            String dropoffTime = fields[3];

            float pickupLong = Float.parseFloat(fields[6]);
            float pickupLat = Float.parseFloat(fields[7]);

            float dropoffLong = Float.parseFloat(fields[8]);
            float dropoffLat = Float.parseFloat(fields[9]);

            if (fields[6].length() == 0 || fields[7].length() == 0 ||
                    pickupLat == 0 || pickupLong == 0) {
                // extract the hours from pickup time
                // format of pick up timeis  (yyyy-mm-dd hh:mm:ss)
                int hours = Integer.parseInt(pickupTime.split(" ")[1].split(":")[0]);

                context.write(new IntWritable(hours), new LongWritable(1));
            }

            if (fields[8].length() == 0 || fields[9].length() == 0 ||
                    dropoffLat == 0 || dropoffLong == 0) {
                int hours = Integer.parseInt(dropoffTime.split(" ")[1].split(":")[0]);

                context.write(new IntWritable(hours), new LongWritable(1));
            }
        }
	}


    public void cleanup(Context context) throws IOException, InterruptedException {
        validator.endValidation();
    }
}
