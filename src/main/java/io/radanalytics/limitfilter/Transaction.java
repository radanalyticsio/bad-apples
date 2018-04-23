package io.radanalytics.limitfilter;

import java.io.Serializable;

/**
 * Simple POJO bject for transactions
 */
//TODO decide what fields need to be in this for returned data
public class Transaction implements Serializable
{
    private Double time;
    private String amount;
    private Double v1;
    private boolean fraudulent = false;

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
     * @return Double
     */
    public Double getV1() {
        return v1;
    }

    /**
     *
     * @return boolean
     */
    public boolean isFraudulent() {
        return fraudulent;
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

    /**
     *
     * @param v1
     */
    public void setV1(Double v1) {
        this.v1 = v1;
    }

    /**
     *
     * @param fraudulent
     */
    public void setFraudulent(boolean fraudulent) {
        this.fraudulent = fraudulent;
    }
}
