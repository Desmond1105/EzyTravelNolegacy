package my.edu.utar.ezytravelnolegacy;

public class Trip {
    private int tripNum;
    private String tripName;
    private int duration;

    public Trip(){ }

    public Trip(int tripNum, String tripName, int duration) {
        this.tripNum = tripNum;
        this.tripName = tripName;
        this.duration = duration;
    }

    public int getTripNum() {
        return tripNum;
    }

    public String getTripName() {
        return tripName;
    }

    public int getDuration() { return duration; }

    public void setTripNum(int tripNum) {
        this.tripNum = tripNum;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setDuration(int duration) { this.duration = duration; }
}
