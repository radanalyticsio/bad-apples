package io.radanalytics.limitfilter;

/**
 * Simple POJO bject for transactions
 */
//TODO decide what fields need to be in this for returned data
public class Transaction
{
    private Double time;
    private String amount;

    /**
     *
     * @return Double
     */
    public Double getTime() {
        return time;
    }

    /**
     *
     * @return String
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param time
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
