package com.sabi.globaladmin.validations;


import com.sabi.globaladmin.dto.requestdto.*;
import com.sabi.globaladmin.exceptions.BadRequestException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Country;
import com.sabi.globaladmin.model.Role;
import com.sabi.globaladmin.model.State;
import com.sabi.globaladmin.repository.*;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@SuppressWarnings("All")
@Slf4j
@Service
public class CoreValidations {
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private CountryRepository countryRepository;
    private StateRepository stateRepository;
    private LGARepository lgaRepository;

    public CoreValidations(RoleRepository roleRepository, PermissionRepository permissionRepository,CountryRepository countryRepository,
                           StateRepository stateRepository,LGARepository lgaRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.lgaRepository = lgaRepository;
    }

    public void validateRole(RoleDto roleDto) {
        if (roleDto.getName() == null || roleDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if (roleDto.getName().length() < 2 || roleDto.getName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");

    }

    public void validatePermission(PermissionDto permissionDto) {

        if (permissionDto.getName() == null || permissionDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if (permissionDto.getName().length() < 2 || permissionDto.getName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");

        if (permissionDto.getAppPermission() == null || permissionDto.getAppPermission().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "App permission cannot be empty");
    }




    public void validateUser(UserDto userDto) {

        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "First name cannot be empty");
        if (userDto.getFirstName().length() < 2 || userDto.getFirstName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid first name  length");
        if (userDto.getLastName() == null || userDto.getLastName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Last name cannot be empty");
        if (userDto.getLastName().length() < 2 || userDto.getLastName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid last name  length");

        if (userDto.getEmail() == null || userDto.getEmail().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "email cannot be empty");
        if (!Utility.validEmail(userDto.getEmail().trim()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid Email Address");
        if (userDto.getPhone() == null || userDto.getPhone().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Phone number cannot be empty");
        if (userDto.getPhone().length() < 8 || userDto.getPhone().length() > 14)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid phone number  length");
        if (!Utility.isNumeric(userDto.getPhone()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for phone number ");
        if(userDto.getRoleId()== null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role id can not be null ");
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid Role"));
    }






    public void updateUser(UserDto userDto) {

        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "First name cannot be empty");
        if (userDto.getFirstName().length() < 2 || userDto.getFirstName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid first name  length");

        if (userDto.getLastName() == null || userDto.getLastName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Last name cannot be empty");
        if (userDto.getLastName().length() < 2 || userDto.getLastName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid last name  length");
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "email cannot be empty");
        if (!Utility.validEmail(userDto.getEmail().trim()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid Email Address");
        if (userDto.getPhone() == null || userDto.getPhone().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Phone number cannot be empty");
        if (userDto.getPhone().length() < 8 || userDto.getPhone().length() > 14)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid phone number  length");
        if (!Utility.isNumeric(userDto.getPhone()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for phone number ");

    }



    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (changePasswordDto.getPassword() == null || changePasswordDto.getPassword().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Password cannot be empty");
        if (changePasswordDto.getPassword().length() < 6 || changePasswordDto.getPassword().length() > 20)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid password length");
        if (changePasswordDto.getPreviousPassword() == null || changePasswordDto.getPreviousPassword().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Previous password cannot be empty");


    }


    public void validateState(StateDto stateDto) {
        if (stateDto.getName() == null || stateDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        String valName = stateDto.getName();
        char valCharName = valName.charAt(0);
        if (Character.isDigit(valCharName)){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name can not start with a number");
        }
        Country country = countryRepository.findById(stateDto.getCountryId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid Country id!"));
    }


    public void validateLGA (LGADto lgaDto){
        if (lgaDto.getName() == null || lgaDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");

        String valName = lgaDto.getName();
        char valCharName = valName.charAt(0);
        if (Character.isDigit(valCharName)){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name can not start with a number");
        }

        State state = stateRepository.findById(lgaDto.getStateId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid State id!"));
    }


    public void validateCountry(CountryDto countryDto) {
        if (countryDto.getName() == null || countryDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if(countryDto.getCode() == null || countryDto.getCode().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Code cannot be empty");
    }



    public void validateBank(BankDto bankDto) {

        if (bankDto.getName() == null || bankDto.getName().trim().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if (bankDto.getCode() == null || bankDto.getCode().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Bank code cannot be empty");
    }

    public void validateRolePermission(RolePermissionDto request) {

        if (request.getRoleId() == null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role id cannot be empty");
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid Role"));

        if(request.getPermissionIds().isEmpty()){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Permission id cannot be empty");
        }

    }

}
