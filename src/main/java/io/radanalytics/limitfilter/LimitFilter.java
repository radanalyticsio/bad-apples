package io.radanalytics.limitfilter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class for filtering the data from spark, parsing the dataset to POJOs for Drools
 * and then running the data through the rules engine
 */
public class LimitFilter
{
    public LimitFilter()
    {

    }

    //TODO set the return type
    public void filter(int limit)
    {
        String url = "jdbc:postgresql://postgresl/finance?user=username&password=password";
        Properties properties = new Properties();
        properties.setProperty("driver","org.postgresql.Driver");

        //test with csv

        //create a spark session
        //TODO add in oshinko
        SparkSession spark = SparkSession.builder().appName("filter").getOrCreate();


        Dataset df = spark.read().jdbc(url, "transactions",properties).toDF();
        //coloumn constraint
        Dataset result = df.where("Amount >"+limit);
        //insert back into postgresql
        String table = "results";
        result.write().mode("append").jdbc(url, table,properties);

        //List<Transaction> transactions = parseResult(result);
        //executeDroolsRules(transactions);

    }

    /**
     * Takes the rows in the dataset and sets the time and the ammount for the Transaction POJO
     * @param result
     * @return
     */
    public List<Transaction> parseResult(Dataset result)
    {
        //TODO add in variance values to return
        List<Transaction> transactions = new ArrayList<Transaction>();
        List<Row> rows = result.collectAsList();
        for (Row row: rows)
        {
            Transaction t = new Transaction();
            t.setTime((Double)row.get(0));
            t.setAmount((String)row.get(29));
            transactions.add(t);
        }
        return transactions;
    }

    //TODO
    public void executeDroolsRules(List<Transaction> transactions)
    {
        //TODO add code to insert the new
    }
}
