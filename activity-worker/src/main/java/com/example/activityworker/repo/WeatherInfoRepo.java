package com.example.activityworker.repo;

import com.example.activityworker.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepo extends JpaRepository<WeatherInfo, Long> {

}
