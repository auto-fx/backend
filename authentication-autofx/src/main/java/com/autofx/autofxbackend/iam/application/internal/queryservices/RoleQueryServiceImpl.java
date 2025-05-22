package com.autofx.autofxbackend.iam.application.internal.queryservices;

import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.queries.GetAllRolesQuery;
import com.autofx.autofxbackend.iam.domain.services.RoleQueryService;
import com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleQueryServiceImpl implements RoleQueryService {

    private RoleRepository roleRepository;

    @Override
    public List<Role> execute(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }
}
