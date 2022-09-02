package my.edu.utar.ezytravelnolegacy;

public class spendingHelper {

    String tripName;
    String spendDescription;
    String spendAmount;


    String spendDate;

    public spendingHelper() {
    }

    public spendingHelper(String tripName, String spendDescription, String spendAmount, String spendDate) {
        this.tripName = tripName;
        this.spendDescription = spendDescription;
        this.spendAmount = spendAmount;
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

    public String getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(String spendAmount) {
        this.spendAmount = spendAmount;
    }


}
