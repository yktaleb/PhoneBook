package com.phonebook.persistence.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;

public class TypeStorage {
    public List<String> get() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        return arguments;
    }

    public static void main(String[] args) {

//            Properties props = new Properties();
//            InputStream is = null;
//
//            // First try loading from the current directory
//            try {
//                File f = new File("application.properties");
//                is = new FileInputStream( f );
//            }
//            catch ( Exception e ) { is = null; }
//
//            try {
//                if ( is == null ) {
//                    // Try loading from classpath
//                    is = getClass().getResourceAsStream("server.properties");
//                }
//
//                // Try loading properties from the file (if found)
//                props.load( is );
//            }
//            catch ( Exception e ) { }
//
//            serverAddr = props.getProperty("ServerAddress", "192.168.0.1");
//            serverPort = new Integer(props.getProperty("ServerPort", "8080"));
//            threadCnt  = new Integer(props.getProperty("ThreadCount", "5"));
    }
}
