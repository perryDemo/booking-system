package com.agoda.clone.agoda.service;

import java.util.Map;

public interface ReviewService {
    public Map<String, Object> findByPropertyID(int propertyid, int page, int size);
}
