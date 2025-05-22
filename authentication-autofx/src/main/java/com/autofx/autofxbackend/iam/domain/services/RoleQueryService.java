package com.autofx.autofxbackend.iam.domain.services;

import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.queries.GetAllRolesQuery;

import java.util.List;

public interface RoleQueryService {

    List<Role> execute (GetAllRolesQuery query);


}
