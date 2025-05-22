package com.autofx.autofxbackend.iam.interfaces.rest.role;

import com.autofx.autofxbackend.iam.domain.model.queries.GetAllRolesQuery;
import com.autofx.autofxbackend.iam.domain.services.RoleQueryService;
import com.autofx.autofxbackend.iam.interfaces.rest.role.resource.RoleResource;
import com.autofx.autofxbackend.iam.interfaces.rest.role.transform.RoleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/authentication-autofx/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
public class RoleController {

    private final RoleQueryService roleQueryService;

    @GetMapping
    public ResponseEntity<List<RoleResource>> getRoles() {
        var getAllRolesQuery = new GetAllRolesQuery();
        var roles = roleQueryService.execute(getAllRolesQuery);
        var roleResource = roles.stream().map(role -> RoleResourceFromEntityAssembler.toResourceFromEntity(role)).toList();
        return ResponseEntity.ok(roleResource);
    }


}
