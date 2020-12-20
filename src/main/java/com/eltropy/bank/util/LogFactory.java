//package com.eltropy.bank.util;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.core.LoggerContext;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@Service
//public class LogFactory {
//
//    private static final Logger LOGGER = LogManager.getRootLogger();//will be used to log till we init the log.xml
//
//    public LogFactory() throws URISyntaxException {
//        LOGGER.info("log factory initialized");
//        //System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
//        //System.setProperty("log4j2.AsyncQueueFullPolicy", "Discard");
//        String log4jConfigFile = "/ENV-LOCAL"
//                + File.separator + "log.xml";
//        System.setProperty("log4j.configurationFile", log4jConfigFile);
//
////        LoggerContext context = (LoggerContext) LogManager.getContext(false);
//
////		InputStream is = LogFactory.class.getClassLoader()
////                .getResourceAsStream(log4jConfigFile);
////		File file = new File(is);
//        // this will force a reconfiguration
////        context.setConfigLocation(new URI(log4jConfigFile));
//        LOGGER.info(log4jConfigFile);
//        LOGGER.info("Logging Enabled");
//    }
//
//    public static Logger getLogger(Class<?> clazz) {
//        return LogManager.getLogger(clazz);
//    }
//
//}
