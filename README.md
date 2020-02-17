# Fuel-scanner
The Fuel Scanner is an application for managing receipts from petrol stations.

## Features
- Receipt scannning (detection of date, price per litre, litres and fuel cost)
- Receipt history
- Entering the kilometer counter state
- Stats such as: fuel usage, fuel cost etc. (charts and tables)
- Login and registration system
<p>
<img src="https://i.imgur.com/CKaVGwD.png" alt="demo1" width="420"/>
<img src="https://i.imgur.com/2gCzTYY.png" alt="demo2" width="420"/>
<img src="https://i.imgur.com/T9lrG7A.png" alt="demo3" width="420"/>
<img src="https://i.imgur.com/5SPXL0A.png" alt="demo4" width="420"/>
</p>

## Technologies
- Spring Boot 2.2
- Angular 8.2
- Tesseract
- OpenCV
- H2

## Usage
To run the application you will need <a href="https://git-scm.com/">Git</a>, <a href="https://nodejs.org/en/download/">Node.js</a> and <a href="https://www.oracle.com/java/technologies/javase-downloads.html">Java</a> installed on your computer.
Firstly clone this repo and go to the project directory.
```shell
$ git clone https://github.com/mbalcer/Fuel-scanner.git
$ cd Fuel-scanner
```

### Backend
Run the application using maven plugin in your IDE using the command:
```shell
$ mvn spring-boot:run
```

### Frontend
You must install dependencies, build and run the application. You will then be able to access it at localhost:4200.

```shell
# Install dependencies
$ npm install

# Build application
$ ng build

# Start application
$ ng serve
```

## Demo
The application in polish is available here: https://fuel-scanner.herokuapp.com/

## Authors
- <a href="https://github.com/mbalcer"> mbalcer </a>
- <a href="https://github.com/matkuc003"> matkuc003 </a>
- <a href="https://github.com/hubigabi"> hubigabi </a>
- <a href="https://github.com/Szymon1109"> Szymon1109 </a>
- <a href="https://github.com/Ebikus"> Ebikus </a>
