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
    public MetricData convertAll(CSVRecord record) {
        MetricData m = new MetricData();

        m.setTimestamp(record.get(MetricEnum.Timestamp));
        m.setWW_supply_flow(record.get(MetricEnum.WW_supply_flow));
        m.setWW_ammonia_measurement(record.get(MetricEnum.WW_ammonia_measurement));
        m.setWW_Secondary_Sediment_Flow(record.get(MetricEnum.WW_Secondary_Sediment_Flow));
        m.setWW_effluent_flow_measurement_(record.get(MetricEnum.WW_effluent_flow_measurement_));
        m.setWW_effluent_recirculation_flow_measurment(record.get(MetricEnum.WW_effluent_recirculation_flow_measurment));
        m.setWW_dry_substance_measurement(record.get(MetricEnum.WW_dry_substance_measurement));
        m.setWW_sec_Sediment_dry_substance_measurement(record.get(MetricEnum.WW_sec_Sediment_dry_substance_measurement));
        m.setWW_effluent_jack_Flow(record.get(MetricEnum.WW_effluent_jack_Flow));
        m.setWW_section_1_air_flow_measurement(record.get(MetricEnum.WW_section_1_air_flow_measurement));
        m.setWW_section_2_air_flow_measurement(record.get(MetricEnum.WW_section_2_air_flow_measurement));
        m.setWW_section_3_air_flow_measurement(record.get(MetricEnum.WW_section_3_air_flow_measurement));
        m.setWW_Settled_Solids(record.get(MetricEnum.WW_Settled_Solids));
        m.setWW_Precipitate_mm(record.get(MetricEnum.WW_Precipitate_mm));
        m.setWW_nitrate_measurement_BLT_4_comp__3(record.get(MetricEnum.WW_nitrate_measurement_BLT_4_comp__3));
        m.setWW_nitrate_measurement_BLT_4_comp__5_of_9(record.get(MetricEnum.WW_nitrate_measurement_BLT_4_comp__5_of_9));
        m.setWW_nitrate_measurement_BLT_4_comp__6_of_10(record.get(MetricEnum.WW_nitrate_measurement_BLT_4_comp__6_of_10));
        m.setWW_nitrate_measurement_Before_Aeration(record.get(MetricEnum.WW_nitrate_measurement_Before_Aeration));
        m.setWW_Aeration_Point_BLT_4_comp__10_(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__10_));
        m.setWW_Aeration_Point_BLT_4_comp__3(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__3));
        m.setWW_Aeration_Point_BLT_4_comp__4(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__4));
        m.setWW_Aeration_Point_BLT_4_comp__5_(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__5_));
        m.setWW_Aeration_Point_BLT_4_comp__6(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__6));
        m.setWW_Aeration_Point_BLT_4_comp__7(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__7));
        m.setWW_Aeration_Point_BLT_4_comp__8_(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__8_));
        m.setWW_Aeration_Point_BLT_4_comp__9(record.get(MetricEnum.WW_Aeration_Point_BLT_4_comp__9));
        m.setWW_Temperature_Measurement_BLT_4(record.get(MetricEnum.WW_Temperature_Measurement_BLT_4));
        m.setWW_Turbid_Measurement(record.get(MetricEnum.WW_Turbid_Measurement));
        m.setWW_Oxygen_Measurement_BLT_4_comp__10(record.get(MetricEnum.WW_Oxygen_Measurement_BLT_4_comp__10));
        m.setWW_Oxygen_Measurement_BLT_4_comp__4(record.get(MetricEnum.WW_Oxygen_Measurement_BLT_4_comp__4));
        m.setWW_Oxygen_Measurement_BLT_4_comp__6(record.get(MetricEnum.WW_Oxygen_Measurement_BLT_4_comp__6));
        m.setWW_Oxygen_Measurement_BLT_4_comp__8(record.get(MetricEnum.WW_Oxygen_Measurement_BLT_4_comp__8));
        m.setWW_Oxygen_Measurement__Before_Aeration_Section_2_(record.get(MetricEnum.WW_Oxygen_Measurement__Before_Aeration_Section_2_));


        return m;
    }
}
