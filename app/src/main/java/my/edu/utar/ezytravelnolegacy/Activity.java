package my.edu.utar.ezytravelnolegacy;

public class Activity {
    private int activityNum;
    private String activityType;
    private String description;
    private String date;
    private String time;

    public Activity(){}

    public Activity(int activityNum, String activityType, String description, String date, String time) {
        this.activityNum = activityNum;
        this.activityType = activityType;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(int activityNum) {
        this.activityNum = activityNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
