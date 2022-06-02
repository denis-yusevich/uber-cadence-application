package com.example.activityworker.activities.repo;

import com.example.activityworker.activities.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepo extends JpaRepository<WeatherInfo, Long> {

}
