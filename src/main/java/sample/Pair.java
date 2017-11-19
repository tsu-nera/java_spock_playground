package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pair {

    public Boolean isMD(Long id) throws SQLException {
        int nbCount;
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5434/sampleDB", "postgres", "postgres");

        PreparedStatement stmt = conn.prepareStatement("select count(*) as cnt from NB;");
        ResultSet rs = stmt.executeQuery();
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
                    .map(this::isReady)
                    .filter(r -> r == Boolean.FALSE)
                    .count();

            if (rCount > 1) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private Boolean isReady(String status) {
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
