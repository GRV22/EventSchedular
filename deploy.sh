mvn clean install
ps ax|grep tomcat |grep -v grep| awk '{print $1}' |xargs kill -9
sudo rm -rf /usr/local/tomcat/webapps/SampleUI-1.0-SNAPSHOT /usr/local/tomcat/webapps/SampleUI-1.0-SNAPSHOT.war
sudo cp target/SampleUI-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
/usr/local/tomcat/bin/./catalina.sh start