package sample;

import lombok.Setter;

import java.sql.*;

public class Sample2Job implements Job {

    Sample2Exec exec;

    @Setter
    private Long id;

    @Override
    public void prepare() throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");
        PreparedStatement stmt = conn.prepareStatement("SELECT model from STORAGE;");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String model = (String) rs.getObject(1);
        System.out.println(model);
        if (id == null) {
            throw new IllegalArgumentException("Invalid id");
        } else if (model.equals("Small")) {
            if (1L > id || 16L < id) {
                throw new IllegalArgumentException("Invalid id:" + id);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        exec = new Sample2Exec(id, 0L, 0L);
        exec.prepare();
    }

    @Override
    public void execute() {
        exec.execute();
    }

    @Override
    public void revert() {
        exec.revert();
    }
}


