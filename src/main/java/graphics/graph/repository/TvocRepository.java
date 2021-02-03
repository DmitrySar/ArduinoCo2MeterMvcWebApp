package graphics.graph.repository;

import graphics.graph.entity.CO2Sensor;
import graphics.graph.entity.TvocSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TvocRepository extends CrudRepository<TvocSensor, Integer> {
    List<TvocSensor> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}
