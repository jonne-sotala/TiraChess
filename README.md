# TiraChess

A simple chess AI in Java. 

## Weekly reports

- [Week 1](./documentation/weekly-reports/week-1.md)
- [Week 2](./documentation/weekly-reports/week-2.md)
- [Week 3](./documentation/weekly-reports/week-3.md)
- [Week 4](./documentation/weekly-reports/week-4.md)
- [Week 5](./documentation/weekly-reports/week-5.md)

## Documentation

- [Requirements specification](./documentation/requirements-specification.md)
- [Implementation document](./documentation/implementation-document.md)
- [Testing document](./documentation/testing-document.md)
- [User guide](./documentation/user-guide.md)

## Usage

### Requirements

The application requires you to have [java](https://docs.oracle.com/en/java/javase/15/install/overview-jdk-installation.html) and [gradle](https://gradle.org/install/) installed on your computer. The application has been developed using the latest Java version 15. However, it should work on older versions as well.  

### Run application

Application can be run using command:
```
gradle run
```

### Testing

Run tests and get test reports:
```
gradle test
```
The test reports will be saved at [index.html](./app/build/reports/jacoco/test/html/index.html) and you can examine them by opening the HTML file on your browser. 

### Checkstyle

Style checks can be run by command:
```
gradle check
```
The checkstyle report will be saved [main.html](./app/build/reports/checkstyle/main.html) and you can open it in your browser.

