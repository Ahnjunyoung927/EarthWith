package com.kh.eco.stats.model.service;
import java.util.List;
import java.util.Map;

public interface StatService {
    Map<String, Object> getLandingStats();

	Map<String, Object> getDashboardStats();

	Map<String, Object> getMainpage();

	List<Map<String, Object>> getRankingStats();
}