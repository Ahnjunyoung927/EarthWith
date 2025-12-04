package com.kh.eco.stats.model.service;
import java.util.Map;

public interface StatService {
    Map<String, Object> getLandingStats();

	Map<String, Object> getDashboardStats();
}