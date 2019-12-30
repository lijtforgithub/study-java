package com.ljt.study.dp.decorator;

import com.ljt.study.juc.ThreadUtils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * @author LiJingTang
 * @date 2019-12-15 21:50
 */
public class LoggerDecorator {

    public static void main(String[] args) {
        ILogger existObj = new FileLogger();
        ILogger newObj = new XMLLogger(existObj);
        String[] s = {"how", "are", "you"};

        for (String value : s) {
            newObj.log(value);
            ThreadUtils.sleepSeconds(1);
        }

        System.out.println("END");
    }

    private static final String PATH = "D:/Workspace/IDEA/study/test/log.txt";
    private static final String RN = System.lineSeparator();

    private static class XMLLogger extends Decorator {

        public XMLLogger(ILogger logger) {
            super(logger);
        }

        @Override
        public void log(String msg) {
            String s = "<msg>" + RN +
                    "<content>" + msg + "</content>" + RN +
                    "<time>" + new Date().toString() + "</time>" + RN +
                    "<msg>" + RN;

            logger.log(s);
        }
    }

    private static class UpLogger extends Decorator {

        public UpLogger(ILogger logger) {
            super(logger);
        }

        @Override
        public void log(String msg) {
            msg = msg.toUpperCase();
            logger.log(msg);
        }
    }

    private static class FileLogger implements ILogger {

        @Override
        public void log(String msg) {
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(new FileOutputStream(PATH, true));
                dos.writeBytes(msg + RN);
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ConsoleLogger implements ILogger {

        @Override
        public void log(String msg) {
            System.out.println(msg);
        }
    }

    abstract static class Decorator implements ILogger {

        protected ILogger logger;

        public Decorator(ILogger logger) {
            this.logger = logger;
        }
    }

    private interface ILogger {

        void log(String msg);

    }

}
