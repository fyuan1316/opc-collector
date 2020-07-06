package io.alauda.consumer;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;

public class TestKafakWrite {

    public static String topic = "test";//定义主题

    public static void main(String[] args) {

        Options opts = new Options();
        opts.addOption("brokers", true, "brokers");
        opts.addOption("topic", true, "topic");
        opts.addOption("protocol", true, "protocol");
        opts.addOption("sasl_mechanism", true, "sasl_mechanism");
        opts.addOption("username", true, "sasl username");
        opts.addOption("password", true, "sasl password");

        BasicParser parser = new BasicParser();

        CommandLine cl = null;
        try {
            cl = parser.parse(opts, args);
        } catch (ParseException e) {
            System.err.printf("parse cli args error:%s\n", e.getStackTrace());
            System.exit(1);
        }
        //
        String username = "kafka";
        String password = "kafka@Tbds.com";
        String broker = "SASL_PLAINTEXT://tbds-172-27-0-6:6668";
        String protocol = "SASL_PLAINTEXT";
        String SASL_MECHANISM = "PLAIN";
        String topic = "test";
        boolean jaas = false;
        if (cl.hasOption("brokers")) {
            broker = cl.getOptionValue("brokers");
        }
        if (cl.hasOption("username")) {
            username = cl.getOptionValue("username");
        }
        if (cl.hasOption("password")) {
            password = cl.getOptionValue("password");
        }
        if (cl.hasOption("protocol")) {
            protocol = cl.getOptionValue("protocol");
            jaas = true;
        }
        if (cl.hasOption("sasl_mechanism")) {
            SASL_MECHANISM = cl.getOptionValue("sasl_mechanism");
            jaas = true;
        }
        if (cl.hasOption("topic")) {
            topic = cl.getOptionValue("topic");
        }


        System.out.printf("broker:%s\n", broker);
        System.out.printf("topic:%s\n", topic);

        System.out.printf("username:%s\n", username);
        System.out.printf("password:%s\n", password);
        System.out.printf("protocol:%s\n", protocol);
        System.out.printf("sasl_mechanism:%s\n", SASL_MECHANISM);

        System.out.printf("jaas:%b\n", jaas);


        Properties props = new Properties();
        props.put("bootstrap.servers", broker);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        if (jaas) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, protocol);
            props.put(SaslConfigs.SASL_MECHANISM, SASL_MECHANISM);
            props.put("sasl.jaas.config",
                    String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", username, password));
        }

//        Properties p = new Properties();
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.23.76:9092,192.168.23.77:9092");//kafka地址，多个地址用逗号分割
/*
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

 */
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
//        try {
            /*
            while (true) {
                String msg = "Hello," + new Random().nextInt(100);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, msg);
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + msg);
                Thread.sleep(500);
            }
*/
        String data = "aaa";
        ProducerRecord<String, String> producerRecord = null;

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            producerRecord = new ProducerRecord(topic, data + i);
            kafkaProducer.send(producerRecord,
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
            System.out.println("flush producer");
            kafkaProducer.flush();
        }
        System.out.println("close producer");
        kafkaProducer.close();


//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            kafkaProducer.close();
//        }


    }
}
