package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private Double temp;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public WeatherInfo(WeatherInfoResponseDto dto) {
        this.city = dto.getCity();
        this.temp = dto.getTemp();
    }

    @PrePersist
    private void setDateTime() {
        dateTime = LocalDateTime.now();
    }

}
