package EventService.demo.service;

import EventService.demo.model.Event;
import EventService.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Event createEvent(Event event) {
        validateOrganizer(event.getOrganizerId());
        validateEventData(event);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("Event ID cannot be null or empty");
        return eventRepository.findById(id);
    }

    public List<Event> getEventsByOrganizer(String organizerId) {
        if (organizerId == null || organizerId.trim().isEmpty()) throw new IllegalArgumentException("Organizer ID cannot be null or empty");
        return eventRepository.findByOrganizerId(organizerId);
    }

    public List<Event> searchEventsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Event title cannot be null or empty");
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    public Event updateEvent(String id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        validateOrganizer(eventDetails.getOrganizerId());
        validateEventData(eventDetails);

        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setLocation(eventDetails.getLocation());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setCapacity(eventDetails.getCapacity());

        return eventRepository.save(event);
    }

    public void deleteEvent(String id) {
        if (!eventRepository.existsById(id)) throw new RuntimeException("Event not found with id: " + id);
        eventRepository.deleteById(id);
    }

    private void validateOrganizer(String organizerId) {
        if (organizerId == null || organizerId.trim().isEmpty()) throw new IllegalArgumentException("Organizer ID cannot be null or empty");

        try {
            String userServiceUrl = "http://UserService/api/users/" + organizerId;
            ResponseEntity<UserResponse> response = restTemplate.getForEntity(userServiceUrl, UserResponse.class);

            if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("User not found with id: " + organizerId);

            UserResponse user = response.getBody();
           
            if (user == null || user.getRoles() == null || !user.getRoles().contains("EVENT_MANAGER") ) {
                throw new RuntimeException("User is not authorized to create events.  role allowed.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("User not found with id: " + organizerId);
        } catch (Exception e) {
            throw new RuntimeException("Error validating user: " + e.getMessage());
        }
    }

    private void validateEventData(Event event) {
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) throw new IllegalArgumentException("Event title is required");
        if (event.getLocation() == null || event.getLocation().trim().isEmpty()) throw new IllegalArgumentException("Event location is required");
        if (event.getStartDate() == null) throw new IllegalArgumentException("Event start date is required");
        if (event.getEndDate() == null) throw new IllegalArgumentException("Event end date is required");
        if (event.getCapacity() == null || event.getCapacity() <= 0) throw new IllegalArgumentException("Event capacity must be greater than 0");
    }

    // Updated UserResponse to match UserService roles
    public static class UserResponse {
        private String id;
        private String username;
        private String email;
        private List<String> roles; // <-- fixed to list
        private String phone;
        private String companyName;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public List<String> getRoles() { return roles; } // <-- getter for list
        public void setRoles(List<String> roles) { this.roles = roles; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
    }
}
