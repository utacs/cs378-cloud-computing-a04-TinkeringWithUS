package edu.cs.utexas.HadoopEx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class EarningsReducer extends Reducer<Text, Text, Text, FloatWritable> {

    private final int DESIRED_RANKINGS = 10;

    private HashMap<String, EarningItem> driverToEarningItem = new HashMap<>();

    public void reduce(Text text, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        String driverId = text.toString(); 

        for(Text value : values) {
            String[] data = value.toString().split(",");
            float earnings = Float.parseFloat(data[0]);
            float tripTime = Float.parseFloat(data[1]);

            if(driverToEarningItem.containsKey(driverId)) {
                EarningItem prevItem = driverToEarningItem.get(driverId);
                prevItem.totalEarnings += earnings;
                prevItem.totalTimeSeconds += tripTime;
            } else {
                driverToEarningItem.put(driverId, new EarningItem(driverId, earnings, tripTime));
            }
        }
    }

    public void cleanup(Context context) throws IOException, InterruptedException {
        ArrayList<EarningItem> itemsToSort = new ArrayList<>(driverToEarningItem.values());

        itemsToSort.sort(new Comparator<EarningItem>() {
            @Override  
            public int compare(EarningItem o1, EarningItem o2) {
                float thisEarningRatio = o1.totalEarnings / o1.totalTimeSeconds;
                float otherEarningRatio = o2.totalEarnings / o2.totalTimeSeconds;

                if (o1.totalTimeSeconds == 0) {
                    thisEarningRatio = 0;
                }

                if (o2.totalTimeSeconds == 0) {
                    otherEarningRatio = 0;
                }

                return -Float.compare(thisEarningRatio, otherEarningRatio);
            };
        });

        Iterator<EarningItem> itr = itemsToSort.iterator();

        for(int rank = 0; rank < DESIRED_RANKINGS; rank++) {
            EarningItem item = itr.next();

            float earningsPerMinute = item.totalEarnings / ( item.totalTimeSeconds / 60);
            context.write(new Text(item.driverId), new FloatWritable(earningsPerMinute));
        }
    }
}