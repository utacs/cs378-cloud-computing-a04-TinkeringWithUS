package edu.cs.utexas.HadoopEx;

public class EarningItem implements Comparable<EarningItem>{
    public String driverId;
    public float totalTimeSeconds;
    public float totalEarnings;

    public EarningItem(String driverId, float totalEarnings, float totalTimeSeconds) {
        this.driverId = driverId;
        this.totalEarnings = totalEarnings;
        this.totalTimeSeconds = totalTimeSeconds;
    }

    @Override
    public int compareTo(EarningItem o) {

        float thisEarningRatio = totalEarnings / totalTimeSeconds;
        float otherEarningRatio = o.totalEarnings / o.totalTimeSeconds;

        if(totalTimeSeconds == 0) {
            thisEarningRatio = 0;
        }

        if(o.totalTimeSeconds == 0) {
            otherEarningRatio = 0;
        }

        return -Float.compare(thisEarningRatio, otherEarningRatio);
    }
}
