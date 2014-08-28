Mad Crawler
===========

### About
Mad Crawler is a multithreaded robot when parses predefined sites and finds all `<a href="" />` links on those ones.
Each target site processes recursively with a one hundred links depth and one seconds timeouts. 
Result links save uniquely to the output `result-[date&time].txt` file. This file will be saved inside a running application folder.

### Build
Project building requires JDK 7+ and the internet connection.
Here is the bash command which tests and builds Mad Crawler:
```
sh gradlew clean build
```
You can find result jar file here:
```
project-root/build/libs/Mad-Cravler-x.x.jar
```
All necessary dependencies are included in it.

### Run
To start program you need JRE 7+ and a file with targets for crawling.
The run command looks like:
```
java -jar Mad-Crawler-x.x.jar /path/to/file-with-targets.txt
```
Where `file-with-targets.txt` should conform to the following format:
```
google.com
apple.com
yandex.com
```
