package graphics.graph.repository;

import graphics.graph.entity.HumiditySensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumidityRepository extends CrudRepository<HumiditySensor, Integer> {
}
