# healthcare-api


for downloading and installing wildfly+keycloak adapter use : `mvn clean initialize -Pwildfly-init`  
for running tests : `mvn test -Pwildfly-managed` or packaging : `mvn package -P wildfly-test-managed` or simply just skip tests with `-DskipTests=true`  
its important to install required modules for wildfly with : `mvn install -Pwildfly-run,postgresql -DskipTests=true`  
for starting initialized wildfly app server : `mvn wildfly:start -Pwildfly-run` and deploy with : `mvn wildfly:deploy -Pwildfly-run` and finally shutdown with `mvn wildfly:shutdown -Pwildfly-run`  
