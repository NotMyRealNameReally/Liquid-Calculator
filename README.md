# Liquid-Calculator

It's made with swing and it's designed to create and store recipes for DIY eLiquids.
eLiquid is the "juice" used in e-cigarettes. It consists of nicotine, propylene glycol, vegetable glycerin and flavour concentrates.

Theme used in the app: https://github.com/bulenkov/Darcula

## How to run

### Prerequisites

```
* JDK 1.8
* Gradle
* MySQL Database
```

### Installing
#### Setting up the database
You just need to run these 2 statements to set it up:


```
CREATE TABLE recipes(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(30) NOT NULL,
    strength DOUBLE NOT NULL,
    pg_vg_ratio DOUBLE NOT NULL,
    volume DOUBLE NOT NULL,
    steep_time INT,
    concentrates JSON,
    PRIMARY KEY(id)
 );
```
and
```
CREATE TABLE concentrates(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(30) NOT NULL,
    flavour_profile VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
 );
```
### Connecting to the database

Right now connection credentials are defined directly in code in the connect() method in model.Database,
so you will need to edit this method and supply it with information about your database.

```java
public void connect() throws SQLException {
        if (connection != null) return;
        String host = "jdbc:mysql://hostname.com:port/databaseName";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(host, "login", "password");
    }
```
### Creating a .jar file
Just run the fatJar gradle task.
## Usage
* Right now the ui is in polish, but if someone ever wants to use it I can translate it.
* The app is not secure at all, so if you plan on using it, make sure that only people you know get access to it.
* To load a recipe double click on one from the list on the left.
* To remove a recipe right click it to bring up the option, recipes can only be removed by their author to prevent mistakes.
* Measuring in drops is not exactly precise, right now it's set to 1 drop = 0,05 ml.
* To add a concentrate you need to create it if it's not in the database yet.

