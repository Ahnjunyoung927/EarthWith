package com.kh.eco.stats.model.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kh.eco.stats.model.dao.StatMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatMapper statMapper;

    @Override
    public Map<String, Object> getLandingStats() {
        return statMapper.selectLandingStats();
    }

	@Override
	public Map<String, Object> getDashboardStats() {
		return statMapper.selectDashboardStats();
	}

	@Override
	public Map<String, Object> getMainpage() {

		return statMapper.selectMainpage();
	}

	@Override
	public List<Map<String, Object>> getRankingStats() {
		
		return statMapper.getRankingStats();
	}
}