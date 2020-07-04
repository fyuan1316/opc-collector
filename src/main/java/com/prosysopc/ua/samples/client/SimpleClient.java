/**
 * Prosys OPC UA Java SDK
 * Copyright (c) Prosys OPC Ltd.
 * <http://www.prosysopc.com>
 * All rights reserved.
 */
package com.prosysopc.ua.samples.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.client.UaClient;
import com.prosysopc.ua.stack.application.Session;
import com.prosysopc.ua.stack.application.SessionChannel;
import com.prosysopc.ua.stack.builtintypes.DataValue;
import com.prosysopc.ua.stack.builtintypes.ExpandedNodeId;
import com.prosysopc.ua.stack.builtintypes.LocalizedText;
import com.prosysopc.ua.stack.builtintypes.NodeId;
import com.prosysopc.ua.stack.core.*;
import com.prosysopc.ua.stack.transport.security.SecurityMode;

/**
 * A very minimal client application. Connects to the server and reads one variable. Works with a
 * non-secure connection.
 */
public class SimpleClient {

    public static List<ExpandedNodeId> metrics = new ArrayList<ExpandedNodeId>();
    public static List<NodeId> metrics2 = new ArrayList<NodeId>();
    public static String[] metricsNameArr = new String[]{"v1", "v2"};

    public static boolean isNameMatched(String name, ReferenceDescription ref) {
        return name.equalsIgnoreCase(ref.getDisplayName().toString());
    }

    public static void setVarsNode(UaClient client) throws Exception {
        List<ReferenceDescription> rootList = client.getAddressSpace().browse(Identifiers.ObjectsFolder);
        for (ReferenceDescription ref : rootList) {
            System.out.println(ref.getDisplayName());
            if (!isNameMatched("simulation", ref)) {
                continue;
            }
            ExpandedNodeId simulationNode = ref.getNodeId();
            List<ReferenceDescription> simulationList = client.getAddressSpace().browse(simulationNode);
            for (ReferenceDescription simRef : simulationList) {
                if (!isNameMatched("test", simRef)) {
                    continue;
                }
                ExpandedNodeId testNode = simRef.getNodeId();
                List<ReferenceDescription> testList = client.getAddressSpace().browse(testNode);
                for (ReferenceDescription varsRef : testList) {
                    for (String name : metricsNameArr) {
                        if (!isNameMatched(name, varsRef)) {
                            continue;
                        }
                        metrics.add(varsRef.getNodeId());
                        metrics2.add(varsRef.getReferenceTypeId());

                    }
                }
            }
        }
        if (metrics.size() == 0) {
            throw new Exception("metrics is empty or not founded");
        }
    }

    public static void main(String[] args) throws Exception {
//    UaClient client = new UaClient("opc.tcp://localhost:52520/OPCUA/SampleConsoleServer");
        UaClient client = new UaClient("opc.tcp://192.168.0.109:53530/OPCUA/SimulationServer");
//        UaClient client = new UaClient("opc.tcp://192.168.0.111:62652/IntegrationObjects/UAServerSimulator1");
        client.setSecurityMode(SecurityMode.NONE);
        initialize(client);
        client.connect();
        DataValue value = client.readValue(Identifiers.Server_ServerStatus_State);

        setVarsNode(client);

        System.out.println(metrics.size());

        DataValue v2 = client.readValue(metrics.get(0));
        System.out.println(v2); //ok
        ExpandedNodeId[] ExpandedNodeIdArr = new ExpandedNodeId[metrics.size()];
        DataValue[] values = client.readValues(metrics.toArray(ExpandedNodeIdArr), null, TimestampsToReturn.Both, 0.0d);
        for (DataValue data : values) {
            System.out.println(data.getValue());
        }
//        NodeId[] NodeIdArr = new NodeId[metrics.size()];
//        DataValue[] values2 = client.readValues(metrics2.toArray(NodeIdArr), null, TimestampsToReturn.Both, 0.0d);
//        for (DataValue data : values2) {
//            System.out.println(data.getValue());
//        }

//        Identifiers.RootFolder
//        ReadValueId.Builder build = new ReadValueId.Builder();
//        build.setAttributeId(123).build();


        System.out.println(value);
        client.disconnect();
    }

    /**
     * Define a minimal ApplicationIdentity. If you use secure connections, you will also need to
     * define the application instance certificate and manage server certificates. See the
     * SampleConsoleClient.initialize() for a full example of that.
     */
    protected static void initialize(UaClient client) throws SecureIdentityException, IOException, UnknownHostException {
        // *** Application Description is sent to the server
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setApplicationName(new LocalizedText("SimpleClient", Locale.ENGLISH));
        // 'localhost' (all lower case) in the URI is converted to the actual
        // host name of the computer in which the application is run
        appDescription.setApplicationUri("urn:localhost:UA:SimpleClient");
        appDescription.setProductUri("urn:prosysopc.com:UA:SimpleClient");
        appDescription.setApplicationType(ApplicationType.Client);

        final ApplicationIdentity identity = new ApplicationIdentity();
        identity.setApplicationDescription(appDescription);
        client.setApplicationIdentity(identity);
    }

}
