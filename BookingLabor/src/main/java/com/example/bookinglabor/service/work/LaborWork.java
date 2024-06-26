package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.LaborMapper;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Labor;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.CityRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.LaborService;
import com.example.bookinglabor.service.work.excel.UploadJob;
import com.example.bookinglabor.service.work.excel.UploadLabor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LaborWork implements LaborService {

    private final LaborRepo laborRepo;
    private final UserRepo userRepository;
    private final CityRepo cityRepository;


    @Override
    public List<Labor> findAllLabors() {

        List<Labor> labors = laborRepo.findAll();

        return labors.stream()
                .map(LaborMapper::mapToLabor)
                .collect(Collectors
                .toList());
    }

    @Override
    public List<Labor> getAllLaborApi() {

        List<Labor> labors = laborRepo.findAll();

        return labors.stream()
                .map(LaborMapper::mapToLaborApi)
                .collect(Collectors
                .toList());
    }

    @Override
    public Labor findJobById(Long id) {

        Optional<Labor> laborOptional = laborRepo.findById(id);

        return laborOptional.map(LaborMapper::mapToLabor).orElse(null);
    }

    @Override
    public Labor findById(Long id) {
        Optional<Labor> laborOptional = laborRepo.findById(id);

        return laborOptional.map(LaborMapper::mapToLabor).orElse(null);
    }


    @Override
    public Labor findByUserId(Long user_id) {

        Labor labor = laborRepo.findLaborByUserId(user_id);

        if(labor != null)
            return laborRepo.findLaborByUserId(user_id);

        else return null;
    }


    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

        if(UploadJob.isValidExcelFile(file)){
            try {

                List<Labor> labors = UploadLabor.getLaborFromExcel(file.getInputStream());
                this.laborRepo.saveAll(labors);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save!!!");
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void createLaborByUserIdAndCityId(Long user_id, Long city_id, Labor labor) {

        UserAccount user = userRepository.findById(user_id).get();
        City city = cityRepository.findById(city_id).get();
        Labor labor_create = LaborMapper.mapToLabor(labor);
        assert labor_create != null;
        labor_create.setUserAccount(user);
        labor_create.setCity(city);
        laborRepo.save(labor_create);


    }

    @Override
    public void update(Long userId, Long city_id, Labor labor) {

        UserAccount user = userRepository.findById(userId).get();
        City city = cityRepository.findById(city_id).get();
        Labor labor_update = LaborMapper.mapToLabor(labor);
        assert labor_update != null;
        labor_update.setUserAccount(user);
        labor_update.setCity(city);
        laborRepo.save(labor_update);
    }
}
