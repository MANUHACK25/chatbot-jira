package com.lodgingApplication.domain.domainRepository;

import com.lodgingApplication.model.Place;

import java.util.List;

public interface LodgingService {
    public List<Place> searchPlaces(String prompt);

}
