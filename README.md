# steam-id-back
## About
#### A backend for https://github.com/0iskak/steam-id-front
Return as JSON format profile general info, profile image url, steam ids, ban statuses and other things.
### Built With JDK 17 and
* [Gradle](https://gradle.org/)
* [Spark](https://sparkjava.com/)
* [Lombok](https://projectlombok.org/)
* [Jackson](https://github.com/FasterXML/jackson)
## Usage
`https://steam-id-back.herokuapp.com/profile/'customurl or steam64 id'`
#### example
https://steam-id-back.herokuapp.com/profile/gabelogannewell \
or \
https://steam-id-back.herokuapp.com/profile/76561197960287930
## Getting Started
1. Get API key from https://steamcommunity.com/dev/apikey
2. Clone the repo
    ```
    git clone https://github.com/0iskak/steam-id-back.git
    ```
3. Change variables
    1. Change port in App.java, line: 
        ```
        port(Integer.parseInt(System.getProperty("PORT")));
        ```
    2. Change API in UserUtil.java, line: 
        ```
        static String API = System.getenv("API");
        ```
4. Go to project's root directory and run by `.\gradlew run` \
or `.\gradlew build; java -jar app/build/libs/app-all.jar`
