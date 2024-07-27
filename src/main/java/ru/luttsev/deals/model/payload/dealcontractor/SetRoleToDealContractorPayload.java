package ru.luttsev.deals.model.payload.dealcontractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для установки роли контрагенту сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на установку роли контрагенту сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetRoleToDealContractorPayload {

    @Schema(description = "ID контрагента сделки")
    @JsonProperty("contractor_id")
    private String contractorId;

    @Schema(description = "ID роли")
    @JsonProperty("role_id")
    private String roleId;

}
