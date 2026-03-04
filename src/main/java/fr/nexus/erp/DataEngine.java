package fr.nexus.erp;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

import static org.apache.spark.sql.functions.col;

public class DataEngine {

    public static void runPipeline() {
        System.setProperty("hadoop.home.dir", "C:\\Hadoop\\hadoop-3.3.6");
        System.load("C:\\Hadoop\\hadoop-3.3.6\\bin\\hadoop.dll");

        SparkSession spark = SparkSession.builder()
                .appName("Nexus ERP Pipeline")
                .master("local[*]")
                .getOrCreate();


        String url = "jdbc:mysql://localhost:3306/nexus_erp";
        String user = "root";
        String password = "";

        Properties connectionProp = new Properties();
        connectionProp.put("user", user);
        connectionProp.put("password", password);
        connectionProp.put("driver", "com.mysql.cj.jdbc.Driver");


        Dataset<Row> dfMySQL = spark.read()
                .jdbc(url, "server_logs", connectionProp);

        System.out.println("DONNEES BRUTES :");
        dfMySQL.show();


        Dataset<Row> dfSelect = dfMySQL.select(
                col("nom_serveur"),
                col("region_serveur"),
                col("statut_serveur")
        );


        Dataset<Row> dfClean = dfSelect.filter(col("region_serveur").isNotNull());


        Dataset<Row> dfSorted = dfClean.orderBy(
                col("region_serveur"),
                col("nom_serveur")
        );

        System.out.println("DONNEES NETTOYEES ET TRIEES :");
        dfSorted.show();


        dfSorted.write()
                .mode("overwrite")
                .partitionBy("region_serveur")
                .json("archives_logs_serveurs");


        spark.stop();
    }
}

