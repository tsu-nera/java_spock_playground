package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pair {
    PreparedStatement stmt;
    ResultSet rs;
    Connection conn;

    public Boolean isMD(Long id) throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");

        return (isPairDown(id) || isFlagOn(id) || isMastersDown(id));
    }

    private Boolean isPairDown(Long id) throws SQLException {
        Long pairId = getPairId(id);

        String targetName = String.format("Node#%s", pairId.toString());
        stmt = conn.prepareStatement(String.format("SELECT state as status from NODE where name = '%s';", targetName));
        rs = stmt.executeQuery();
        rs.next();
        String status = rs.getString("status");

        return !isReadyStatus(status);
    }

    private Boolean isFlagOn(Long id) throws SQLException {
        String targetName = String.format("Node#%s", id.toString());
        stmt = conn.prepareStatement(String.format("SELECT flag1, flag2, flag3, flag4 from NODE where name = '%s';", targetName));
        rs = stmt.executeQuery();
        rs.next();
        Boolean flag1 = rs.getBoolean(1);
        Boolean flag2 = rs.getBoolean(2);
        Boolean flag3 = rs.getBoolean(3);
        Boolean flag4 = rs.getBoolean(4);
        return flag1 || flag2 || flag3 || flag4;
    }

    private Boolean isMastersDown(Long id) throws SQLException {
        int nbCount;
        stmt = conn.prepareStatement("select count(*) as cnt from NB;");
        rs = stmt.executeQuery();
        rs.next();
        nbCount = Integer.parseInt(rs.getString("cnt"));

        List<Long> nList = new ArrayList<>();
        if (nbCount < 3){
            return Boolean.FALSE;
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
            long rCount = nList.stream()
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
                    .map(this::isReadyStatus)
                    .filter(r -> r == Boolean.FALSE)
                    .count();

            if (rCount > 1) {
                return Boolean.TRUE;
            }
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
}
