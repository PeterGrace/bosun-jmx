bosun-jmx
=========

Input JMX statistics to Bosun/opentsdb.  Based roughly on code found in the jmx_examples documentation as well as an example from [middlewaremagic.com](http://middlewaremagic.com/jboss/?p=324)

Requirements to Run
------------
- A [bosun](http://bosun.org) or [opentsdb](http://opentsdb.net) server.  Docker version [here](http://registry.hub.docker.com/u/stackexchange/bosun)
- [scollector](http://github.com/bosun-monitor/scollector)

Requirements to compile
--------------
- Apache Ant
- Java

Installation + Usage
------------
bosun-jmx.jar accepts a single argument which is a path to a properties file.  The three variables available are host, port and app-under-test. host and port are related to where the JMX debug port is located.  app-under-test is a string field you can use to differentiate a particular java process in the output (produces a tagk/v of 'appname' in the collector output.)  If you use a properties file, you will need to specify it as an argument to the bosun-jmx.jar file.  In the case of scollector, you will want to instead locate bosun-jmx.jar outside of the external collectors directory tree and instead use a shell script that calls the java with the properties file argument.

- clone repo
- execute `ant` from repo directory
- make bosun-jmx.jar executable
- move bosun-jmx.jar into your continuous-run scollector external collectors directory (e.g. /opt/scollector/collectors/0)
- restart scollector
