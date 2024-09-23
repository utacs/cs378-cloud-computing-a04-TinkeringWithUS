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

        // TODO: enhance validation to cover float casting for  
        // pickup lat/long, dropoff lat/long
        ErrorType errorType = validator.validateLine(line);

        if(errorType == ErrorType.GPS_ERROR) {
            String[] fields = line.split(",");

            String pickupTime = fields[2];
            String dropoffTime = fields[3];

            String pickupLong = fields[6];
            String pickupLat = fields[7];

            String dropoffLong = fields[8];
            String dropoffLat = fields[9];

            if(pickupLat.length() == 0 || pickupLong.length() == 0 || 
                    pickupLat.equals("0") || pickupLong.equals("0")) {
                // extract the hours from pickup time
                // format of pick up timeis  (yyyy-mm-dd hh:mm:ss)
                int hours = Integer.parseInt(pickupTime.split(" ")[1].split(":")[0]);

                context.write(new IntWritable(hours), new LongWritable(1));
            }

            if(dropoffLat.length() == 0 || dropoffLong.length() == 0 || 
                    dropoffLat.equals("0") || dropoffLong.equals("0")) {
                int hours = Integer.parseInt(dropoffTime.split(" ")[1].split(":")[0]);

                context.write(new IntWritable(hours), new LongWritable(1));
            }
        }
	}


    public void cleanup(Context context) throws IOException, InterruptedException {
        validator.endValidation();
    }
}
