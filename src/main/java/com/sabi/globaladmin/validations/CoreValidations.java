package com.sabi.globaladmin.validations;


import com.sabi.globaladmin.dto.requestdto.UserDto;
import com.sabi.globaladmin.exceptions.BadRequestException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Role;
import com.sabi.globaladmin.repository.PermissionRepository;
import com.sabi.globaladmin.repository.RoleRepository;
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

    public CoreValidations(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

//    public void validateRole(RoleDto roleDto) {
//        if (roleDto.getName() == null || roleDto.getName().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
//        if (roleDto.getName().length() < 2 || roleDto.getName().length() > 100)// NAME LENGTH*********
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");
//
//    }

//    public void validatePermission(PermissionDto permissionDto) {
//
//        if (permissionDto.getName() == null || permissionDto.getName().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
//        if (permissionDto.getName().length() < 2 || permissionDto.getName().length() > 100)// NAME LENGTH*********
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");
//
//        if (permissionDto.getCode() == null || permissionDto.getCode().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "code cannot be empty");
//    }

//    public void validateRolePermission(RolePermissionDto rolePermissionDto) {
//        if ((Long) rolePermissionDto.getRoleId() == null)
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role Id cannot be empty");
//        if (rolePermissionDto.getPermissionIds() == null)
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role permission(s) cannot be empty");
//        roleRepository.findById(rolePermissionDto.getRoleId())
//                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
//                        " Enter a valid Role"));
//        rolePermissionDto.getPermissionIds().forEach((p) -> {
//            permissionRepository.findById(p).orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
//                    " Permission " + p + " Does not exist"));
//        });
//    }


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



//    public void changePassword(ChangePasswordDto changePasswordDto) {
//        if (changePasswordDto.getPassword() == null || changePasswordDto.getPassword().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Password cannot be empty");
//        if (changePasswordDto.getPassword().length() < 6 || changePasswordDto.getPassword().length() > 20)// NAME LENGTH*********
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid password length");
//        if (changePasswordDto.getPreviousPassword() == null || changePasswordDto.getPreviousPassword().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Previous password cannot be empty");
//
//
//    }



}
