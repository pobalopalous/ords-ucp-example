Used to test the basic JDBC and UCP connection to a target database and proxying to a REST Enabled schema.

This test compiles a class using standard JDBC and UCP jars but it is expected that you run the class using the JDBC and UCP jars that ship with Oracle REST Data Services ( ORDS ).

To build, run 
`mvn clean package`

Get those jars using the following steps:
```
jar -x -f ~/Downloads/ords-dist-23.4.0.344.1008/ords.war WEB-INF/lib/ojdbc11-21.12.0.0.230906.jar
jar -x -f ~/Downloads/ords-dist-23.4.0.344.1008/ords.war WEB-INF/lib/ucp11-21.12.0.0.230906.jar  
```

It will create two jars in a directory called WEB-INF/lib/

Using a different version of ORDS? Then the ojdbc11 and ucp11 jar filenames will be different. Use these commands to find out what jar files you should use:
```
jar -x -f ~/Downloads/ords-dist-23.4.0.344.1008/ords.war | grep "ojdbc11"
jar -x -f ~/Downloads/ords-dist-23.4.0.344.1008/ords.war | grep "ucp11" 
```


To run the class:

`java -cp target/ords-ucpproxytest-1.0.jar:WEB-INF/lib/ojdbc11-21.12.0.0.230906.jar:WEB-INF/lib/ucp11-21.12.0.0.230906.jar blog.peterobrien.ords.ucp.UCPProxyTest jdbc:oracle:thin:@localhost:1521/freepdb1 ORDS_PUBLIC_USER oracle HR`
