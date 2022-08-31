package com.ysmind.parkingcontrol.controllers;

import com.ysmind.parkingcontrol.dtos.ParkingSpotDTO;
import com.ysmind.parkingcontrol.models.ParkingSpotModel;
import com.ysmind.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600) //permitir conexoes de qualquer lugar
@RequestMapping("/parking-spot")// final URL
public class ParkingSpotController {

    //criando ponto de injecao
    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    //Metodo POST
    @PostMapping
    public ResponseEntity<Object> saveParkingSpot (@RequestBody @Valid ParkingSpotDTO dto){

        //checar se identificadores duplicados ja existem
        if(parkingSpotService.existsByLicensePlateNumber(dto.getLicensePlateNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Placa ja esta registrada!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(dto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja tem um carro registrado nesse local");
        }
        if(parkingSpotService.existsByApartmentAndBlock(dto.getApartment(), dto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja tem um carro registrado nesse apartamento e bloco");
        }

        var parkingSpotModel = new ParkingSpotModel();
        //convertendo DTO em model
        BeanUtils.copyProperties(dto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        //Salvando dados
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));

    }

    //Metodo GET (Pode se usar Page ou List, no caso de ser paginacao, deve se usar e configurar as tags de pages)
    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        //Configurando pageamento
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    //GET com parametros
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        //Verificar se o optional retornou dados
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma vaga foi encontrada com esse ID");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    //Metodo DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        //Verificar se o optional retornou dados
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma vaga foi encontrada com esse ID");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }

    //Metodo PUT (atualizar campos)
    @PutMapping("/{id}")
    public ResponseEntity<Object> putParkingSpot(@PathVariable(value = "id")UUID id, @RequestBody @Valid ParkingSpotDTO dto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        //Verificar se o optional retornou dados
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma vaga foi encontrada com esse ID");
        }
        /* Verificar e Settar campos manualmente:
        var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingSpotNumber(dto.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateNumber(dto.getLicensePlateNumber());
        parkingSpotModel.setBrandCar(dto.getBrandCar());
        parkingSpotModel.setModelCar(dto.getModelCar());
        parkingSpotModel.setColorCar(dto.getColorCar());
        parkingSpotModel.setResponsibleName(dto.getResponsibleName());
        parkingSpotModel.setApartment(dto.getApartment());
        parkingSpotModel.setBlock(dto.getBlock());
        */

        //Ou, utilizar o BeanUtils para comparar e escolher o que manter (util para tabelas grandes!)

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(dto, parkingSpotModel);
        //definindo os campos que se deseja manter
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

        //Em qualquer um dos dois jeitos, deve se terminar do mesmo jeito
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));

    }

}
