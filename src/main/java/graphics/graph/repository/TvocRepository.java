package graphics.graph.repository;

import graphics.graph.entity.CO2Sensor;
import graphics.graph.entity.TvocSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface TvocRepository extends CrudRepository<TvocSensor, Integer> {
}
