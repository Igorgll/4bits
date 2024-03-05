package com.bits.bits.utils;

import com.bits.bits.dto.UserDTO;
import com.bits.bits.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class FourBitsUtils {

    public static List<UserDTO> convertModelToUserDTO(List<UserModel> userModels) {
        List<UserDTO> dtos = new ArrayList<>();
        for (UserModel userModel : userModels) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(userModel.getName());
            userDTO.setEmail(userModel.getEmail());
            userDTO.setAdmin(userModel.isAdmin());
            userDTO.setActive(userModel.isActive());

            dtos.add(userDTO);
        }

        return dtos;
    }
}
