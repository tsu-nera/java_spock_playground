package sample;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sample2ExecStage1 implements Job {
    PreparedStatement stmt;
    ResultSet rs;
    Connection conn;

    @Setter
    private Long id;

    @Getter
    private boolean result;

    String status;
    boolean flag1;
    boolean flag2;
    boolean flag3;
    boolean flag4;
    List<String> statusList;

    private void reset() {
        status = null;
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        statusList = new ArrayList<>();
    }

    @Override
    public void prepare() throws SQLException {
        reset();
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");

        isPairDownPrepare();
        isFlagOnPrepare();

        int nbCount;
        stmt = conn.prepareStatement("select count(*) as cnt from NB;");
        rs = stmt.executeQuery();
        rs.next();
        nbCount = Integer.parseInt(rs.getString("cnt"));

        List<Long> nList = new ArrayList<>();
        if (nbCount < 3) {
            statusList = null;
        } else if (nbCount == 3) {
            nList.add(1L);
            nList.add(4L);
            nList.add(5L);
            nList.add(2L);
        } else {
            nList.add(1L);
            nList.add(4L);
            nList.add(5L);
            nList.add(8L);
        }

        if (nList.contains(id)) {
            nList.remove(id);
            statusList = nList.stream()
                    .map(i -> "Node#" + i.toString())
                    .map(name -> String.format("select state  as status from NODE where name = '%s';", name))
                    .map(sql -> {
                        try {
                            ResultSet r = conn.prepareStatement(sql).executeQuery();
                            r.next();
                            return r.getString("status");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return "";
                    })
                    .collect(Collectors.toList());
        }
    }

    private void isFlagOnPrepare() throws SQLException {
        String targetName = String.format("Node#%s", id.toString());
        stmt = conn.prepareStatement(String.format("SELECT flag1, flag2, flag3, flag4 from NODE where name = '%s';", targetName));
        rs = stmt.executeQuery();
        rs.next();
        flag1 = rs.getBoolean(1);
        flag2 = rs.getBoolean(2);
        flag3 = rs.getBoolean(3);
        flag4 = rs.getBoolean(4);
    }

    private void isPairDownPrepare() throws SQLException {
        Long pairId = getPairId(id);
        String targetPairName = String.format("Node#%s", pairId.toString());
        stmt = conn.prepareStatement(String.format("SELECT state as status from NODE where name = '%s';", targetPairName));
        rs = stmt.executeQuery();
        rs.next();
        status = rs.getString("status");
    }

    @Override
    public void execute() throws SQLException {
        result = isMD();
    }

    public Boolean isMD() throws SQLException {
        return isPairDown() || isFlagOn() || isMastersDown();
    }

    private Boolean isPairDown() throws SQLException {
        return !isReadyStatus(status);
    }

    private Boolean isFlagOn() throws SQLException {
        return flag1 || flag2 || flag3 || flag4;
    }

    private Boolean isMastersDown() throws SQLException {
        if (statusList == null) {
            return Boolean.FALSE;
        }

        int rCount = (int) statusList.stream()
                .map(this::isReadyStatus)
                .filter(r -> r == Boolean.FALSE)
                .count();

        if (rCount > 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isReadyStatus(String status) {
        switch (status) {
            case "Normal":
                return Boolean.TRUE;
            case "Warning":
                return Boolean.TRUE;
            default:
                return Boolean.FALSE;
        }
    }

    public Long getPairId(Long id) {
        return ((id - 1) ^ 1) + 1;
    }

    @Override
    public void revert() {

    }
}
