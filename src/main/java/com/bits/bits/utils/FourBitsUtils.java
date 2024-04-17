package com.bits.bits.utils;

import com.bits.bits.dto.UserDTO;
import com.bits.bits.model.AdminModel;

import java.util.ArrayList;
import java.util.List;

public class FourBitsUtils {

    public static List<UserDTO> convertModelToUserDTO(List<AdminModel> userModels) {
        List<UserDTO> dtos = new ArrayList<>();
        for (AdminModel userModel : userModels) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userModel.getAdminId());
            userDTO.setName(userModel.getName());
            userDTO.setEmail(userModel.getEmail());
            userDTO.setCpf(userModel.getCpf());
            userDTO.setGroup(userModel.getGroup());
            userDTO.setActive(userModel.isActive());

            dtos.add(userDTO);
        }

        return dtos;
    }
}
