package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.TrophyUserDTO;
import org.alx.fitnessapp.model.entity.TrophyUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrophyUserDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public TrophyUserDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public TrophyUserDTO convertTrophyUserToTrophyUserDTO(TrophyUser trophyUser) {
        return modelMapper.map(trophyUser, TrophyUserDTO.class);
    }

}
