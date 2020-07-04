import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class yaml {

    @Test
    public void Testyaml() {
        System.out.println("123");

        Yaml yaml = new Yaml();

        String yamlstr = "source:\n" +
                "  from: local\n" +
                "  local:\n" +
                "    rate: \"1\" # read number of records per second\n" +
                "  remote:\n" +
                "    url: \"opc://xxx\"  #\n" +
                "\n" +
                "sink:\n" +
                "  to: kafka\n" +
                "  kafka:\n" +
                "    brokerlist: localhost:6667\n" +
                "    topic: test\n" +
                "    msgspliter: \",\"";
        Object obj = new Object();
        yaml.loadAs(yamlstr, obj.getClass());


        System.out.println(obj);
    }
}
