package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.math.util.DoubleArray;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Mapper;

// Mapper<Key in, Value in, Key out, Value out>
public class EarningsMapper extends Mapper<Object, Text, Text, TupleWritable> {
    
    LineValidator validator = new LineValidator();

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

            FloatWritable[] info = new FloatWritable[2];
            info[0] = new FloatWritable(totalAmount);
            info[1] = new FloatWritable(tripTimeSeconds);
            context.write(new Text(driverId), new TupleWritable(info));
        }

    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        validator.endValidation();
    }
}