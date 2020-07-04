package io.alauda.consumer;

import io.alauda.config.Config;
import io.alauda.definition.Consumer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConsumer implements Consumer<MetricData> {
    private Config config;
    private String brokerlist;
    private String topic;
    private String msgspliter;
    private KafkaProducer<String, String> kafkaProducer;

    public KafkaConsumer(Config config) {
        this.config = config;
        this.topic = config.getSink().getKafka().getTopic();
        this.msgspliter = config.getSink().getKafka().getMsgspliter();
        this.brokerlist = config.getSink().getKafka().getBrokerlist();

        this.init();
    }

    private void info() {
        StringBuilder sb = new StringBuilder();
        sb.append("------\n");
        sb.append("sink: kafka\n");
        sb.append(String.format("broker: %s\n", this.brokerlist));
        sb.append(String.format("topic: %s\n", this.topic));
        sb.append(String.format("msg splitter: %s\n", this.msgspliter));
        System.out.println(sb);
        System.out.println("------");
    }

    private void init() {
        Properties p = new Properties();
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.23.76:9092,192.168.23.77:9092");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerlist);//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.kafkaProducer = new KafkaProducer<String, String>(p);

        this.info();
    }

    @Override
    public void consume(Product<MetricData> product) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        MetricData data = product.get();
        System.out.printf("kafka writer will write data: %s\n", data);

        this.writeOut(data);

    }

    private void writeOut(MetricData data) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(this.topic, data.toString());
        this.kafkaProducer.send(record);
    }


}
