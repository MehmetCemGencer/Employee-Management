package com.management.employee.services;

import com.management.employee.entities.Location;
import com.management.employee.repositories.LocationRepository;
import com.management.employee.requests.LocationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService {
    private LocationRepository locationRepository;

    public Optional<Location> findById(int id) {
        return locationRepository.findById(id);
    }

    public Optional<Location> findByAddress(String address) {
        return locationRepository.findByAddress(address);
    }

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location create(LocationRequest request) {
        Location location = Location.builder()
                .name(request.getName())
                .address(request.getAddress())
                .zipCode(request.getZipCode())
                .city(request.getCity())
                .country(request.getCountry())
                .build();

        locationRepository.save(location);

        return location;
    }

    public Location update(Location location, LocationRequest newFields) {
        location.setName(newFields.getName());
        location.setAddress(newFields.getAddress());
        location.setZipCode(newFields.getZipCode());
        location.setCity(newFields.getCity());
        location.setCountry(newFields.getCountry());

        locationRepository.save(location);

        return location;

    }
}
