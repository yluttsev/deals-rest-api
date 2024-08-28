package ru.luttsev.deals.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SetRoleToDealContractorPayload;
import ru.luttsev.deals.service.ContractorRoleService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

/**
 * Контроллер для работы с ролями контрагентов
 *
 * @author Yuri Luttsev
 */
@Tag(name = "contractor-roles", description = "API для работы с ролями контрагентов сделки")
@RestController
@RequestMapping("/contractor-to-role")
@RequiredArgsConstructor
@PreAuthorize("!hasRole('ADMIN')")
public class ContractorToRoleController {

    /**
     * {@link ContractorRoleService Сервис для работы с ролями контрагентов}
     */
    private final ContractorRoleService contractorRoleService;

    /**
     * {@link ModelMapper Маппер сущностей и DTO}
     */
    private final ModelMapper mapper;

    /**
     * Назначает роль контрагенту сделки
     *
     * @param payload {@link SetRoleToDealContractorPayload DTO установки роли}
     * @return {@link DealContractorPayload DTO контрагента сделки} с новой ролью
     */
    @Operation(summary = "Добавление роли контрагенту сделки", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное добавление роли контрагенту сделки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealContractorPayload.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрагент сделки с укзанным ID не найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/add")
    public DealContractorPayload addRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.addRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

    /**
     * Удаляет роль контрагенту сделки
     *
     * @param payload {@link SetRoleToDealContractorPayload DTO установки роли}
     * @return {@link DealContractorPayload DTO контрагента сделки} с удаленной ролью
     */
    @Operation(summary = "Удаление роли у контрагента сделки", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление роли у контрагента сделки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealContractorPayload.class
                                    )
                            )
                    }
            )
    })
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete")
    public DealContractorPayload deleteRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.deleteRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

}
