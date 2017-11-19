package sample;

import java.sql.SQLException;

public interface Job {
    void prepare() throws SQLException;
    void execute() throws SQLException;
    void revert();
}
