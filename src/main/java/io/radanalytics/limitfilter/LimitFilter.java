package io.radanalytics.limitfilter;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

import java.util.ArrayList;
import java.util.Collections;
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
        String url = "jdbc:postgresql://postgresql/finance?user=username&password=password";
        Properties properties = new Properties();
        properties.setProperty("driver","org.postgresql.Driver");

        //create a spark session
        //TODO add in oshinko
        SparkSession spark = SparkSession.builder().appName("filter").getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

        KieBase rules = loadRules();
        Broadcast<KieBase> broadcastRules = sc.broadcast(rules);

        Encoder<Transaction> transactionEncoder = Encoders.bean(Transaction.class);

        Dataset df = spark.read().jdbc(url, "transactions",properties).toDF();
        //coloumn constraint
        Dataset result = df.where("Amount >"+limit)
                           .map((MapFunction<Row, Transaction>) row -> executeDroolsRules(broadcastRules.value(), row), transactionEncoder)
                           .filter((FilterFunction<Transaction>) t -> t.isFraudulent());

        Result fraudulentRecords = new Result();
        fraudulentRecords.setRecordCount(result.count());
        Encoder<Result> fraudulentRecordsEncoder = Encoders.bean(Result.class);
        Dataset<Result> fraudulentRecordsDS = spark.createDataset(
                Collections.singletonList(fraudulentRecords),
                fraudulentRecordsEncoder);

        //insert back into postgresql
        String table = "results";
        fraudulentRecordsDS.write().mode("append").jdbc(url, table, properties);
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
            t.setV1((Double)row.get(1));
            transactions.add(t);
        }
        return transactions;
    }

    public static KieBase loadRules() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        return kieContainer.getKieBase();
    }

    public static Transaction executeDroolsRules(KieBase base, Row row)
    {
        StatelessKieSession session = base.newStatelessKieSession();
        Transaction t = new Transaction();
        t.setTime(Double.parseDouble((String)row.get(0)));
        t.setAmount((String)row.get(29));
        t.setV1(Double.parseDouble((String)row.get(1)));
        session.execute(CommandFactory.newInsert(t));
        return t;
    }
}
