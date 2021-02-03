package graphics.graph.repository;

import graphics.graph.entity.TemperatureSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureSensor, Integer> {
    List<TemperatureSensor> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}
