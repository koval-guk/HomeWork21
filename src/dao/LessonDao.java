package dao;

import model.Homework;
import model.Lesson;
import repo.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

    public void add(Lesson lesson) {
        Connection connection = DataBaseConnection.getConnection();
        assert connection != null;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("insert into Homework (name, description) values (?, ?);", 1, 2);
            statement.setString(1, lesson.getHomework().getName());
            statement.setString(2, lesson.getHomework().getDescription());
            statement.executeUpdate();
            Integer id = null;
            Statement st = connection.createStatement();
            st.executeQuery("select max(id) as id from Homework");
            ResultSet resultSet = st.getResultSet();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                System.out.println(id);
            }
            String name = null;
            st.executeQuery("select name from Homework where id = " + id);
            resultSet = st.getResultSet();
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
            if (lesson.getHomework().getName().equals(name)) {
                lesson.getHomework().setId(id);
            }
            statement = connection.prepareStatement
                    ("insert into Lesson (name, update_at, homework_id) values (?, now(), ?);", 1, 2);
            statement.setString(1, lesson.getName());
            statement.setInt(2, lesson.getHomework().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DataBaseConnection.close(connection);
        }
    }

    public void remove(Lesson lesson) {
        Connection connection = DataBaseConnection.getConnection();
        assert connection != null;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from Lesson where name = \"%s\"", lesson.getName()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ;
        } finally {
            DataBaseConnection.close(connection);
        }
    }

    public List<Lesson> getAllLessons() {
        Connection connection = DataBaseConnection.getConnection();
        assert connection != null;
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery
                    ("select l.id, l.name, l.update_at, l.homework_id, h.name as h_name, h.description " +
                            "from Lesson as l join homework as h where l.homework_id = h.id");
            ResultSet resultSet = statement.getResultSet();
            List<Lesson> list = new ArrayList<>();
            while (resultSet.next()) {
                Homework homework = new Homework(resultSet.getString("h_name"), resultSet.getString("description"));
                homework.setId(resultSet.getInt("homework_id"));
                Lesson lesson = new Lesson(resultSet.getString("name"), homework);
                lesson.setId(resultSet.getInt("id"));
                lesson.setUpdateAt(resultSet.getDate("update_at"));
                list.add(lesson);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DataBaseConnection.close(connection);
        }
        return new ArrayList<>();
    }

    public Lesson getLessonById(int id) {
        Connection connection = DataBaseConnection.getConnection();
        assert connection != null;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("select l.id, l.name, l.update_at, l.homework_id, h.name as h_name, h.description" +
                            " from Lesson as l join Homework as h where l.id = ?");
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            Lesson lesson = null;
            if (resultSet != null) {
                while (resultSet.next()) {
                    Homework homework = new Homework(resultSet.getString("h_name"), resultSet.getString("description"));
                    homework.setId(resultSet.getInt("homework_id"));
                    lesson = new Lesson(resultSet.getString("name"), homework);
                    lesson.setId(resultSet.getInt("id"));
                    lesson.setUpdateAt(resultSet.getDate("update_at"));
                }
            }
            return lesson;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DataBaseConnection.close(connection);
        }
        return null;
    }
}
