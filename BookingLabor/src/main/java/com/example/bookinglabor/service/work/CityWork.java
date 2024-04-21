package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.CityMapper;
import com.example.bookinglabor.model.City;

import com.example.bookinglabor.repo.CityRepo;
import com.example.bookinglabor.service.CityService;
import com.example.bookinglabor.service.work.excel.UploadCity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityWork implements CityService {

    private final CityRepo cityRepo;
    @Override
    public List<City> findAllCities() {

        List<City> cities = cityRepo.findAll();

        return cities.stream()
                .map(CityMapper::mapToCity)
                .collect(Collectors
                .toList());
    }

    @Override
    public List<City> getAllCitiesApi() {

        List<City> cities = cityRepo.findAll();

        return cities.stream()
                .map(CityMapper::mapToCityApi)
                .collect(Collectors
                .toList());
    }

    @Override
    public void deleteById(Long id) {
        cityRepo.deleteById(id);
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

        if(UploadCity.isValidExcelFile(file)){
            try {

                List<City> cities = UploadCity.getCityFromExcel(file.getInputStream());
                this.cityRepo.saveAll(cities);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save !");
                throw new IllegalArgumentException(e);
            }
        }
    }
}
