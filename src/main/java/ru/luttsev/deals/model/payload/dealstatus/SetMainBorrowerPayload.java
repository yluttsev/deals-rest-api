package ru.luttsev.deals.model.payload.dealstatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO установки значения mainBorrower контраенту
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на установку значения признака" +
        " основного заемщика контрагенту сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetMainBorrowerPayload {

    @Schema(description = "ID контрагента сделки")
    @JsonProperty("contractor_id")
    private String contractorId;

    @Schema(description = "Признак основного заемщика")
    @JsonProperty("main_borrower")
    private boolean mainBorrower;

}
