package graphics.graph.entity;

import java.time.LocalDateTime;

public interface ISensor {
    double getValue();
    LocalDateTime getTime();
}
