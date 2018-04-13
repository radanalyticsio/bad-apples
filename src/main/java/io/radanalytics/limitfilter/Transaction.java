package io.radanalytics.limitfilter;

/**
 * Simple POJO bject for transactions
 */
//TODO decide what fields need to be in this for returned data
public class Transaction
{
    private Double time;
    private String amount;


    public Double getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
