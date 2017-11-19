package sample;

import lombok.Setter;

import java.sql.*;

public class Sample2Job implements Job {

    Sample2Exec exec;
    PreparedStatement stmt;
    ResultSet rs;
    Connection conn;

    @Setter
    private Long id;

    @Override
    public void prepare() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");

        stmt = conn.prepareStatement("SELECT model from STORAGE;");
        rs = stmt.executeQuery();
        rs.next();
        String model = (String) rs.getObject(1);

        assertId(model, id);

        String targetName = String.format("Node#%s", id.toString());
        stmt = conn.prepareStatement(String.format("SELECT state from NODE where name = '%s';", targetName));
        rs = stmt.executeQuery();
        rs.next();
        String status = (String) rs.getObject(1);

        assertReadyStatus(status);

        exec = new Sample2Exec(id, 0L, 0L);
        exec.prepare();
    }

    private void assertId(String model, Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id");
        } else if (model.equals("Small")) {
            if (1L > id || 16L < id) {
                throw new IllegalArgumentException("Invalid id:" + id);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void assertReadyStatus(String status) throws IllegalStateException {
        switch (status) {
            case "Normal":
            case "Warning":
            case "Maintenance":
                return;
            default:
                throw new IllegalStateException(String.format("Invalid State:%s", status));
        }
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
