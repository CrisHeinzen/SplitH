package com.tcc.splith.dto;

import com.tcc.splith.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public record GroupResponseDTO(
        Long id,
        String name,
        String description,
        List<UserResponseDTO> members
) {
    public static GroupResponseDTO fromEntity(Group group) {
        List<UserResponseDTO> membersDto = group.getMembers() == null 
                ? List.of() 
                : group.getMembers().stream()
                       .map(UserResponseDTO::fromEntity)
                       .collect(Collectors.toList());

        return new GroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                membersDto
        );
    }
}
