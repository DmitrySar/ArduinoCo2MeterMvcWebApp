package graphics.graph.repository;

import graphics.graph.entity.HumiditySensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HumidityRepository extends CrudRepository<HumiditySensor, Integer> {
    List<HumiditySensor> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}
