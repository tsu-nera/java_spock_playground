package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbUnitSample {
    private static final String SQL = "update HOGE set state = 'error' where name = 'name#3';";

    public void execute() {
        try(Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");
        ) {
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("failed.");
        }
    }
}