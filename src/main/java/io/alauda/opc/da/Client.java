package io.alauda.opc.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
//        String host = "127.0.0.1";// server
//        String domain = "";// domain
//        String progId = "ICONICS.SimulatorOPCDA.2";
//        String user = "OpcUser";// server上的访问用户
//        String password = "xxxxxx";// 访问用户的密码

        // create connection information
        AutoReconnectController autos = null;
        JISystem.setAutoRegisteration(true);
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("192.168.0.222");
        ci.setDomain("");
        ci.setUser("opcuser");
        ci.setPassword("opcpassword");
//        ci.setProgId("SWToolbox.TOPServer.V5");
        ci.setProgId("Matrikon.OPC.Simulation.1");
        // ci.setClsid("680DFBF7-C92D-484D-84BE-06DC3DECCD68"); // if ProgId is not working, try it using the Clsid instead
//         ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305"); // if ProgId is not working, try it using the Clsid instead
//        final String itemId = "_System._Time_Second";
        final String itemId = "d1.m1";
        // create a new server
        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        try {
            // connect to server
//            autos = new AutoReconnectController(server);
//            autos.connect();
            server.connect();

            // add sync access, poll every 500 ms
            final AccessBase access = new SyncAccess(server, 500);
//            final AccessBase access = new Async20Access(server,6000,false);
            access.addItem(itemId, new DataCallback() {
                @Override
                public void changed(Item item, ItemState state) {
                    System.out.println(state);
                }
            });
            // start reading
            access.bind();
            // wait a little bit
            Thread.sleep(10 * 1000);
            // stop reading
            access.unbind();
        } catch (final JIException e) {
            e.printStackTrace();
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
        } catch (AlreadyConnectedException e) {
            e.printStackTrace();
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (AddFailedException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
//        }finally{
//            autos.disconnect();
//        }
    }

}

