package io.alauda.consumer;

import io.alauda.config.Config;
import io.alauda.definition.Consumer;
import io.alauda.definition.Product;
import io.alauda.product.MetricData;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.ArrayList;
import java.util.List;
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

        if (this.config.getSink().getKafka().getSasl_enable() != null && Boolean.parseBoolean(this.config.getSink().getKafka().getSasl_enable())) {
            sb.append(String.format("sasl_enable=%s\n", this.config.getSink().getKafka().getSasl_enable()));
            sb.append(String.format("username=%s\n", this.config.getSink().getKafka().getSasl_username()));
            sb.append(String.format("password=%s\n", this.config.getSink().getKafka().getSasl_passwd()));
            sb.append(String.format("protocol=%s\n", this.config.getSink().getKafka().getProtocol()));
            sb.append(String.format("sasl_mechanism=%s\n", this.config.getSink().getKafka().getSasl_mechanism()));
        }

        System.out.println(sb);
        System.out.println("------");
    }

    private List<String> checkConfig() {
        boolean missing = false;
        ArrayList<String> missingVars = new ArrayList<String>();
        if (this.config.getSink() == null) {
            missingVars.add("sink");
            return missingVars;
        }
        if (this.config.getSink().getKafka() == null) {
            missingVars.add("kafka");
            return missingVars;
        }

        if (this.config.getSink().getKafka().getKey_serializer() == null) {
            missingVars.add("key_serializer");
        }
        if (this.config.getSink().getKafka().getValue_serializer() == null) {
            missingVars.add("value_serializer");
        }
        if (this.config.getSink().getKafka().getSasl_enable() == null) {
            missingVars.add("sasl_enable");
        }
        if (this.config.getSink().getKafka().getProtocol() == null) {
            missingVars.add("protocol");
        }
        if (this.config.getSink().getKafka().getSasl_mechanism() == null) {
            missingVars.add("sasl_mechanism");
        }
        if (this.config.getSink().getKafka().getTopic() == null) {
            missingVars.add("topic");
        }
        return missingVars;
    }

    private void init() {
        List<String> warns = checkConfig();
        if (warns.size() > 0) {
            System.err.printf("config文件中缺失以下配置参数:\n");
            for (int i = 0; i < warns.size(); i++) {
                System.err.println(warns.get(i));
            }
            System.exit(1);
        }

        Properties p = new Properties();
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.23.76:9092,192.168.23.77:9092");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerlist);//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.config.getSink().getKafka().getKey_serializer());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.config.getSink().getKafka().getValue_serializer());

        if (Boolean.parseBoolean(this.config.getSink().getKafka().getSasl_enable())) {
            String username = this.config.getSink().getKafka().getSasl_username();
            String password = this.config.getSink().getKafka().getSasl_passwd();
            p.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, this.config.getSink().getKafka().getProtocol());
            p.put(SaslConfigs.SASL_MECHANISM, this.config.getSink().getKafka().getSasl_mechanism());
            p.put("sasl.jaas.config",
                    String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", username, password));
        }

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
//        this.kafkaProducer.send(record);

        kafkaProducer.send(record,
                new org.apache.kafka.clients.producer.Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if (e != null) {
                            System.out.println("onCompletion exception");
                            e.printStackTrace();
                        }
                        System.out.println("The offset of the record we just sent is: " + metadata);
                    }
                });
//        System.out.println("flush producer");
        kafkaProducer.flush();
//        System.out.println("close producer");
//        kafkaProducer.close();


    }


}
