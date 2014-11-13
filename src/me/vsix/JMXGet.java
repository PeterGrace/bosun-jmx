package me.vsix;

import java.util.Set;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.openmbean.CompositeDataSupport;
import java.util.Properties;
import java.io.FileInputStream;

public class JMXGet {

    /* For simplicity, we declare "throws Exception".
       Real programs will usually want finer-grained exception handling. */
    public static void main(String[] args) throws Exception {

        Properties p = new Properties(System.getProperties());
        if (args.length > 0)
        {
            FileInputStream propertyfile = new FileInputStream(args[0]);
            p.load(propertyfile);
        }
        else
        {
            p.setProperty("host","localhost");
            p.setProperty("port", "9999");
        }
        System.setProperties(p);

        String host=p.getProperty("host");
        String port=p.getProperty("port");

        String serviceurl=String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi", p.getProperty("host"),Integer.parseInt(p.getProperty("port")));

        JMXServiceURL url =
            new JMXServiceURL(serviceurl);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

	while(true)
	{
		getHeapMemoryUsage(mbsc);
		getNonHeapMemoryUsage(mbsc);
		getPoolMemoryUsage(mbsc);
		sleep(1000);
	}


    }
private static void getHeapMemoryUsage(MBeanServerConnection con) throws Exception
{
    long timestamp = System.currentTimeMillis() / 1000l;
    ObjectName memoryMXBean=new ObjectName("java.lang:type=Memory");
    CompositeDataSupport dataSenders = (CompositeDataSupport) con.getAttribute(memoryMXBean,"HeapMemoryUsage");
    if (dataSenders != null)
      {
        Long commited = (Long) dataSenders.get("committed");
        Long init = (Long) dataSenders.get("init");
        Long max = (Long) dataSenders.get("max");
        Long used = (Long) dataSenders.get("used");
        Long percentage = ((used * 100) / max);
        System.out.println("jmx.mem.heap.committed "+timestamp+" "+commited);
        System.out.println("jmx.mem.heap.init "+timestamp+" "+init);
        System.out.println("jmx.mem.heap.max "+timestamp+" "+max);
        System.out.println("jmx.mem.heap.used "+timestamp+" "+used);
        System.out.println("jmx.mem.heap.percent_used "+timestamp+" "+percentage);
       }
}
private static void getNonHeapMemoryUsage(MBeanServerConnection con) throws Exception
{
    long timestamp = System.currentTimeMillis() / 1000l;
    ObjectName memoryMXBean=new ObjectName("java.lang:type=Memory");
    CompositeDataSupport dataSenders = (CompositeDataSupport) con.getAttribute(memoryMXBean,"NonHeapMemoryUsage");
    if (dataSenders != null)
      {
        Long commited = (Long) dataSenders.get("committed");
        Long init = (Long) dataSenders.get("init");
        Long max = (Long) dataSenders.get("max");
        Long used = (Long) dataSenders.get("used");
        Long percentage = ((used * 100) / max);
        System.out.println("jmx.mem.non_heap.committed "+timestamp+" "+commited);
        System.out.println("jmx.mem.non_heap.init "+timestamp+" "+init);
        System.out.println("jmx.mem.non_heap.max "+timestamp+" "+max);
        System.out.println("jmx.mem.non_heap.used "+timestamp+" "+used);
        System.out.println("jmx.mem.non_heap.percent_used "+timestamp+" "+percentage);
       }
}
private static void getPoolMemoryUsage(MBeanServerConnection con) throws Exception
{
        ObjectName MemoryPoolMXBean=new ObjectName("java.lang:type=MemoryPool,name=*");
        Set<ObjectInstance> MemoryPoolResult=con.queryMBeans(MemoryPoolMXBean,null);
	for( ObjectInstance instance : MemoryPoolResult )
	{
	    String bosunname=instance.getObjectName().toString().split(",")[1].split("=")[1].replace(" ","_");
	    long timestamp = System.currentTimeMillis() / 1000l;
	    CompositeDataSupport dataSenders = (CompositeDataSupport) con.getAttribute(instance.getObjectName(),"Usage");

        Long commited = (Long) dataSenders.get("committed");
        Long init = (Long) dataSenders.get("init");
        Long max = (Long) dataSenders.get("max");
        Long used = (Long) dataSenders.get("used");
        Long percentage = ((used * 100) / max);
        System.out.println("jmx.mem.pool.committed "+timestamp+" "+commited+" pooltype="+bosunname);
        System.out.println("jmx.mem.pool.init "+timestamp+" "+init+" pooltype="+bosunname);
        System.out.println("jmx.mem.pool.max "+timestamp+" "+max+" pooltype="+bosunname);
        System.out.println("jmx.mem.pool.used "+timestamp+" "+used+" pooltype="+bosunname);
        System.out.println("jmx.mem.pool.percent_used "+timestamp+" "+percentage+" pooltype="+bosunname);

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
