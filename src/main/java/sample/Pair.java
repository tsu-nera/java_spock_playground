package sample;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    @Setter
    DbAccessor accessor;

    public Long getPairId(Long id) {
        return ((id - 1) ^ 1) + 1;
    }

    public Boolean isMD(Long id, Long nbCount) {
        List<Long> nList = new ArrayList<>();

        if (nbCount == 2){
            nList.add(1L);
            nList.add(4L);
            nList.add(3L);
            nList.add(2L);
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
                    .map(accessor::getStatus)
                    .map(s -> isReady(s))
                    .filter(r -> r == Boolean.FALSE)
                    .count();

            if (rCount > 1) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    private Boolean isReady(String status) {
        if (status == "Normal") {
            return Boolean.TRUE;
        } else if (status == "Warning") {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
