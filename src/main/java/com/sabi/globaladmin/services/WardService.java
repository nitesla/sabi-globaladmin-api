package com.sabi.globaladmin.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.WardDto;
import com.sabi.globaladmin.dto.responsedto.WardResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.LGA;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.model.Ward;
import com.sabi.globaladmin.repository.LGARepository;
import com.sabi.globaladmin.repository.WardRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class WardService {

    private WardRepository wardRepository;
    private LGARepository lgaRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    public WardService(WardRepository wardRepository, LGARepository lgaRepository, ModelMapper mapper, ObjectMapper objectMapper) {
        this.wardRepository = wardRepository;
        this.wardRepository = wardRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    /** <summary>
     * Ward creation
     * </summary>
     * <remarks>this method is responsible for creation of new Ward</remarks>
     */

    public WardResponseDto createWard(WardDto request) {
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Ward ward = mapper.map(request,Ward.class);
        Ward wardExist = wardRepository.findByName(request.getName());
        if(wardExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Ward already exist");
        }
        wardRepository.save(ward);
        log.debug("Create new Ward - {}"+ new Gson().toJson(ward));
        return mapper.map(ward, WardResponseDto.class);
    }

    /** <summary>
     * Ward update
     * </summary>
     * <remarks>this method is responsible for updating already existing Ward</remarks>
     */
    public WardResponseDto updateWard(WardDto request) {
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Ward ward = wardRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested Ward Id does not exist!"));
        mapper.map(request, ward);
        wardRepository.save(ward);
        log.debug("Ward record updated - {}" + new Gson().toJson(ward));
        return mapper.map(ward, WardResponseDto.class);
    }

    /** <summary>
     * Find Ward
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */

    public WardResponseDto findWard(Long id){
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested Ward Id does not exist!"));
        LGA lga = lgaRepository.findById(ward.getLgaId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " LGA Id does not exist!"));
        WardResponseDto response = WardResponseDto.builder()
                .id(ward.getId())
                .name(ward.getName())
                .lgaId(ward.getLgaId())
                .lgaName(lga.getName())
                .build();
        return response;
    }


    public Page<Ward> findAll(String name, Long lgaId, PageRequest pageRequest ){
        Page<Ward> ward = wardRepository.findWard(name,lgaId,pageRequest);
        if(ward == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        ward.getContent().forEach(wards -> {
            LGA lga = lgaRepository.getOne(wards.getLgaId());

            wards.setLgaName(lga.getName());
        });
        return ward;
    }




    public List<Ward> getAll(String name,Long lgaId){
        List<Ward> wards = wardRepository.listWard(name,lgaId);
        for (Ward tran : wards
        ) {
            LGA lga = lgaRepository.getOne(tran.getLgaId());
            tran.setLgaName(lga.getName());
        }
        return wards;

    }



}
