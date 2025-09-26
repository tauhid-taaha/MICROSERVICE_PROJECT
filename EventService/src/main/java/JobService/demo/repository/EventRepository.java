package EventService.demo.repository;

import EventService.demo.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByOrganizerId(String organizerId);
    List<Event> findByTitleContainingIgnoreCase(String title);
}
