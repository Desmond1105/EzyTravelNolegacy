package my.edu.utar.ezytravelnolegacy;

public class spendingHelper {

    String tripName;
    String spendDescription;
    double spendAmount;
    String spendDate;

    public spendingHelper() {
    }

    public spendingHelper(String tripName, String spendDescription, double spendAmount, String spendDate) {
        this.tripName = tripName;
        this.spendDescription = spendDescription;
        this.spendAmount = spendAmount;
        this.spendDate = spendDate;
    }


    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getSpendDescription() {
        return spendDescription;
    }

    public void setSpendDescription(String spendDescription) {
        this.spendDescription = spendDescription;
    }

    public double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public String getSpendDate() {
        return spendDate;
    }

    public void setSpendDate(String spendDate) {
        this.spendDate = spendDate;
    }

}
