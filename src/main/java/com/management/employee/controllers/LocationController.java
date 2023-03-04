package com.management.employee.controllers;

import com.management.employee.entities.Location;
import com.management.employee.requests.LocationRequest;
import com.management.employee.responses.ErrorResponse;
import com.management.employee.responses.SuccessResponse;
import com.management.employee.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Location> locations = locationService.getAll();

        SuccessResponse response = SuccessResponse.builder().data(locations).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable int id
    ) {
        Optional<Location> location = locationService.findById(id);

        if (location.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Location Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        SuccessResponse response = SuccessResponse.builder().data(location.get()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody LocationRequest request
            ) {
        Optional<Location> location = locationService.findByAddress(request.getAddress());

        if (location.isPresent()) {
            ErrorResponse response = ErrorResponse.builder().message("Location Already Exists").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Location newLocation = locationService.create(request);

        SuccessResponse response = SuccessResponse.builder()
                .data(newLocation).message("Location Created").build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(
            @PathVariable int id,
            @Valid @RequestBody LocationRequest request
    ) {
        Optional<Location> location = locationService.findById(id);

        if (location.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Location Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Location updatedLocation = locationService.update(location.get(), request);

        SuccessResponse response = SuccessResponse.builder()
                .data(updatedLocation).message("Location Updated").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
