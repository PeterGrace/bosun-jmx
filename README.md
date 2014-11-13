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
- clone repo
- execute `ant` from repo directory
- make bosun-jmx.jar executable
- move bosun-jmx.jar into your continuous-run scollector external collectors directory (e.g. /opt/scollector/collectors/0)
- restart scollector
