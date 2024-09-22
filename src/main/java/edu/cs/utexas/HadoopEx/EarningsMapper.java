package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Mapper<Key in, Value in, Key out, Value out>
public class EarningsMapper extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();
    
    private LineValidator validator = new LineValidator();

    // Create a counter and initialize with 1
    // private final IntWritable counter = new IntWritable(1);
    // // Create a hadoop text object to store words
    // private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // (key = byte offsets, value = line content) (hadoop textinput format)
        String line = value.toString();

        ErrorType errorType = validator.validateLine(line);

        // Refactor this
        String[] fields = line.split(",");
        String driverId = fields[1];

        if(errorType != ErrorType.INVALID_LINE) {
            Float totalAmount = Float.parseFloat(fields[16]) ;
            Float tripTimeSeconds = Float.parseFloat(fields[4]);

            word.set(driverId);

            // TODO: replace with hand made data type
            context.write(word, new Text(totalAmount + "," + tripTimeSeconds));
        }

    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        validator.endValidation();
    }
}