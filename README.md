# JournalApp
In this practical, you build an app that uses the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html). The app, called JournalApp, stores a list of journals in a Room database and displays the list in a RecyclerView. The JournalApp app is basic, but sufficiently complete that you can use it as a template to build on.

The JournalApp app does the following:

* Works with a database to get and save journals
* Displays all the journals in a RecyclerView in MainActivity.
* Opens a second Activity when the user taps the + FAB button. When the user enters a journal, the app adds the journal to the database and then the list updates automatically.
* Opens the same second Activity when user taps a journal in the RecyclerView to edit it.
* On the second Activity , the user may choose a specific date for the journal. If not, the journal take the current date.
* Do not initializes the data in the database if there is no existing data.
* Add a menu item that allows the user to delete all the data.
* You also enable the user to swipe a journal to delete it from the database.

## Application overview

<img width="200" src="./assets/d5fc2f939857886a.png"> <img width="200" src="./assets/76bc03f5b9dfc7bd.png"> <img width="200" src="./assets/c05628264d3bba8f.png">

<img width="200" src="./assets/23119bc45fea366e.png"> <img width="200" src="./assets/69aabda87116615.png"> <img width="200" src="./assets/4d56a00d3be16245.png">

<img width="200" src="./assets/f1f0dc44c201fbce.png"> <img width="200" src="./assets/a199ecb3b100bf8.png">
 
**Journal**: Confirmation Alerts are optionals

You must follow the diagram below for your database table

![journal_table](./assets/f9b37f9107621615.png)

## IMPORTANT
in order to store complex data like Date in RoomDatabase you need to use converters:

```java
public class Converters {
   @TypeConverter
   public static Date fromTimestamp(Long value) {
       return value == null ? null : new Date(value);
   }

   @TypeConverter
   public static Long dateToTimestamp(Date date) {
       return date == null ? null : date.getTime();
   }
}
```
 
then add the following annotation to your RoomDatabase class under @Database :

 ```java
@Database(entities = ...)
@TypeConverters({Converters.class})
```
