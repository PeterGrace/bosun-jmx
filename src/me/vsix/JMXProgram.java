package me.vsix;

import me.vsix.JMXGet;

public class JMXProgram {

        /* For simplicity, we declare "throws Exception".
               Real programs will usually want finer-grained exception handling. */
    public static void main(String[] args) throws Exception {
                       JMXGet bjmx = new JMXGet(args);
    while(true)
    {
        bjmx.getHeapMemoryUsage();
        bjmx.getNonHeapMemoryUsage();
        bjmx.getPoolMemoryUsage();
        bjmx.getGcStats();
        sleep(1000);
    }

 }
    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
