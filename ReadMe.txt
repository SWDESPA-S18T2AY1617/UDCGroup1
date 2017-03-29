In order to resolve the error Java class not found exception for the JDBC. Manually add the JDBC library to the project as an extension (Eclipse). 

0. The lib folder goes to the root directory of  your project (the same folder where src folder is)
1. Right click on the project folder and go to properties.
2. Click on the Java Build Path panel.
3. Click "Add an External JARs"
4. Locate the project folder/cloned repo and open the lib folder
5. Add "mysql-connector-java-5.1.40-bin.jar

After adding, apply the changes and it should resolve the error.