package io.alauda.producer.local;

import io.alauda.product.MetricData;
import io.alauda.product.MetricEnum;
import org.apache.commons.csv.CSVRecord;

public class Parser {

    public MetricData convert(CSVRecord record) {
        MetricData m = new MetricData();
        m.setTimestamp(record.get(MetricEnum.Timestamp));
        m.setWW_supply_flow(record.get(MetricEnum.WW_supply_flow));
        return m;
    }
}
