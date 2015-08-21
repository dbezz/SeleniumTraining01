package Entities;

/**
 * Created by dmytro.bezzubikov on 8/20/2015.
 */
public class CarRentExpense
{
    private String personalAmount;
    private String businessAmount;
    private String amount;
    private String transactionDate;
    private String travelDate;
    private String merchant;
    private String booking;
    private String purpose;
    private String expenseType;

    private String pickUpDate;
    private String dropOffDate;
    private String pickUpLocation;
    private String dropOffLocation;

    private String pickUpLocationExp;
    private String dropOffLocationExp;

    private String outReason;

    public String getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getDropOffLocationExp() {
        return dropOffLocationExp;
    }

    public void setDropOffLocationExp(String dropOffLocationExp) {
        this.dropOffLocationExp = dropOffLocationExp;
    }

    public String getPersonalAmount() {
        return personalAmount;
    }

    public void setPersonalAmount(String personalAmount) {
        this.personalAmount = personalAmount;
    }

    public String getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(String businessAmount) {
        this.businessAmount = businessAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public String getPickUpLocationExp() {
        return pickUpLocationExp;
    }

    public void setPickUpLocationExp(String pickUpLocationExp) {
        this.pickUpLocationExp = pickUpLocationExp;
    }

}
