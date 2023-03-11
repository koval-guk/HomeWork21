import dao.LessonDao;
import model.Homework;
import model.Lesson;
import repo.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection connection = DataBaseConnection.getConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table Homework(" +
                    "id int primary key auto_increment," +
                    "name varchar(100) default \"Home Work\", " +
                    "description text)");
            statement.executeUpdate("create table Lesson(" +
                    "id int primary key auto_increment, " +
                    "name varchar(100) default \"lesson\", " +
                    "update_at datetime, " +
                    "homework_id int, " +
                    "foreign key (homework_id) references homework(id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DataBaseConnection.close(connection);
        }


        LessonDao lessons = new LessonDao();
        Homework homework1 = new Homework("homework 1", "bla-bla");
        Homework homework2 = new Homework("homework 2", "bla-bla");
        Homework homework3 = new Homework("homework 3", "bla-bla");
        Lesson lesson1 = new Lesson("lesson 1", homework1);
        Lesson lesson2 = new Lesson("lesson 2", homework2);
        Lesson lesson3 = new Lesson("lesson 3", homework3);
        Lesson lesson4 = new Lesson("lesson 4", homework2);
        Lesson lesson5 = new Lesson("lesson 5", homework2);
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);
        System.out.println(lessons.getAllLessons());
        lessons.remove(lesson3);
        System.out.println(lessons.getAllLessons());
        System.out.println(lessons.getLessonById(5));
    }
}
