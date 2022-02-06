package com.sabi.globaladmin.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.BankDto;
import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.responsedto.BankResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Bank;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.BankRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BankService {

    private BankRepository bankRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final CoreValidations validations;

    public BankService(BankRepository bankRepository, ModelMapper mapper, ObjectMapper objectMapper, CoreValidations validations) {
        this.bankRepository = bankRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.validations = validations;
    }



    /** <summary>
     * Bank creation
     * </summary>
     * <remarks>this method is responsible for creation of new bank</remarks>
     */

    public BankResponseDto createBank(BankDto request) {
        validations.validateBank(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Bank bank = mapper.map(request,Bank.class);
        Bank bankExist = bankRepository.findByName(request.getName());
        if(bankExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Bank already exist");
        }
        bank.setCreatedBy(userCurrent.getId());
        bank.setStatus(1);
        bank = bankRepository.save(bank);
        log.debug("Create new bank - {}"+ new Gson().toJson(bank));
        return mapper.map(bank, BankResponseDto.class);
    }



    /** <summary>
     * Bank update
     * </summary>
     * <remarks>this method is responsible for updating already existing bank</remarks>
     */

    public BankResponseDto updateBank(BankDto request) {
        validations.validateBank(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Bank bank = bankRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested bank id does not exist!"));
        mapper.map(request, bank);
        bank.setUpdatedBy(userCurrent.getId());
        bankRepository.save(bank);
        log.debug("Bank record updated - {}"+ new Gson().toJson(bank));
        return mapper.map(bank, BankResponseDto.class);
    }



    /** <summary>
     * Find Bank
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public BankResponseDto findBank(Long id){
        Bank bank  = bankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested bank id does not exist!"));
        return mapper.map(bank,BankResponseDto.class);
    }


    /** <summary>
     * Find all Banks
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<Bank> findAll(String name,String code, PageRequest pageRequest ){
        Page<Bank> bank = bankRepository.findBanks(name,code,pageRequest);
        if(bank == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return bank;

    }


    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this method is responsible for enabling and dis enabling a bank</remarks>
     */
    public void enableDisable (EnableDisEnableDto request){
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Bank bank  = bankRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested bank Id does not exist!"));
        bank.setStatus(request.getStatus());
        bank.setUpdatedBy(userCurrent.getId());
        bankRepository.save(bank);

    }


    public List<Bank> getAll(int status){
        List<Bank> banks = bankRepository.findByStatus(status);
        return banks;

    }
}
