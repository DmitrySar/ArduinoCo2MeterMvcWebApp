package graphics.graph.repository;

import graphics.graph.entity.CO2Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CO2Repository extends CrudRepository<CO2Sensor, Integer> {
}
