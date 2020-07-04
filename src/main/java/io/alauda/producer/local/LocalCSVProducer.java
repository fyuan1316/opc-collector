package io.alauda.producer.local;

import io.alauda.config.Config;
import io.alauda.definition.Producer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LocalCSVProducer implements Producer<MetricData> {
    private Config config;
    private int recordPointer = 0;
    private int metricSize = 0;

    public LocalCSVProducer(Config config) {
        this.config = config;
        try {
            this.init();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    static ArrayList<MetricData> metrics = null;

    public void init() throws IOException {
        if (metrics == null) {
            this.loadCsvData();
        }
        System.out.println("");
    }

    private void loadCsvData() throws IOException {
        metrics = new ArrayList<MetricData>();
        int cunter = 0;
        Parser parser = new Parser();
//        Source source = this.config.getSource();

        Reader in = new FileReader(this.config.getSource().getLocal().getCsvpath());
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
        MetricData metric;
        for (CSVRecord record : records) {
            cunter++;
            if (cunter % 100 == 0) {
                System.out.printf("loaded %d records\n", cunter);
            }
            metric = parser.convertAll(record);
//            System.out.println(metric);

            metrics.add(metric);
        }
        System.out.printf("loaded %d records\n", cunter);
        this.recordPointer = 0;
        this.metricSize = metrics.size();
    }

    @Override
    public Product<MetricData> produce() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        return new Product<MetricData>() {
            @Override
            public MetricData get() {
//                long t = System.currentTimeMillis();
//                System.out.printf("produce:%d\n", t);
                if (recordPointer >= metricSize) {
                    recordPointer = recordPointer % metricSize;
                }
                MetricData data = metrics.get(recordPointer);
                recordPointer++;

                System.out.printf("idx=%d,%s\n", recordPointer, data);
                return data;

            }
        };
    }
}
