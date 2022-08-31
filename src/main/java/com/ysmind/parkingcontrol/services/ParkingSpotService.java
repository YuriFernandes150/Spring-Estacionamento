package com.ysmind.parkingcontrol.services;

import com.ysmind.parkingcontrol.models.ParkingSpotModel;
import com.ysmind.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service // Ao adicionar essa tag, sao implementados varios metodos direto do JPA
public class ParkingSpotService {

    //criando ponto de injecao
    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository){
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional // garante que um erro nao atrapalhe outras requests
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel); //esse metodo vem do JPA (tag @Service)
    }

    public boolean existsByLicensePlateNumber(String licensePlateNumber){
        return parkingSpotRepository.existsByLicensePlateNumber(licensePlateNumber);//esse metodo vem do JPA (tag @Service)
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber){
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);//esse metodo vem do JPA (tag @Service)
    }

    public boolean existsByApartmentAndBlock(String apartment, String block){
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);//esse metodo vem do JPA (tag @Service)
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);//esse metodo vem do JPA (tag @Service)
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);//esse metodo vem do JPA (tag @Service)
    }

    //Qualquer metodo que possa ser feito em cascata, recomenda-se usar essa tag.
    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);//esse metodo vem do JPA (tag @Service)
    }
}
