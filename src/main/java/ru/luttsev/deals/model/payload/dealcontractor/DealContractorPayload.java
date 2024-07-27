package ru.luttsev.deals.model.payload.dealcontractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.contractorrole.ContractorRolePayload;

import java.util.List;

/**
 * DTO контрагента сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Контрагент сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealContractorPayload {

    @Schema(description = "ID контрагента")
    private String id;

    @Schema(description = "Описание контрагента")
    private String description;

    @Schema(description = "ID контрагента из сервиса контрагентов")
    @JsonProperty("contractor_id")
    private String contractorId;

    @Schema(description = "Имя контрагента")
    private String name;

    @Schema(description = "ИНН контрагента")
    private String inn;

    @Schema(description = "Признак основного заемщика")
    private boolean main;

    @Schema(description = "Список ролей")
    private List<ContractorRolePayload> roles;

}
