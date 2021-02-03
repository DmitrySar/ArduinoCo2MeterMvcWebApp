package graphics.graph.repository;

import graphics.graph.entity.TemperatureSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureSensor, Integer> {
}
