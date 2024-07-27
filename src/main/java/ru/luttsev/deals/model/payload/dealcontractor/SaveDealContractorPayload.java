package ru.luttsev.deals.model.payload.dealcontractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для сохранения контрагента сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на сохранение контрагента сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveDealContractorPayload {

    @Schema(description = "ID контрагента сделки")
    private String id;

    @Schema(description = "ID сделки")
    @JsonProperty("deal_id")
    private String dealId;

    @Schema(description = "ID контрагента из сервиса контрагентов")
    @JsonProperty("contractor_id")
    private String contractorId;

    @Schema(description = "Имя контрагента")
    private String name;

    @Schema(description = "ИНН контрагента")
    private String inn;

    @Schema(description = "Признак основного заемщика")
    private boolean main;

}
