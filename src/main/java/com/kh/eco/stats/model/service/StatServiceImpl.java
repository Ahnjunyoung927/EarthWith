package com.kh.eco.stats.model.service;

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
}