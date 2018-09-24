package io.radanalytics.limitfilter;

import java.io.Serializable;


public class Result implements Serializable {
    private long recordCount;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
}
