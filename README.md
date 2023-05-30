# Appium
**Appium with Java - Preconditions**

Java version 11 - jdk-11.0.17.jdk

Maven version 3 - apache-maven-3.9.0

Installed appium --version 2.0.0-beta.67

Installed Android SDK and set ANDROID_HOME env variable

**Run test**
Start appium command: appium

Install package: mvn clean install

Run test: mvn clean test -DsuiteFile=src/test/resources/Parallel.xml -Dsaucelab_username=<abc> -Dsaucelab_accessKey=<abcd> -Dsaucelab_URL=<url>

Generate report: allure generate --clean

**Note**
saucelab_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub"
