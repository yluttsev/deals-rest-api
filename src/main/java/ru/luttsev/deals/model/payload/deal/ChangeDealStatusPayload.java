package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO установки статуса сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на установку статуса сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeDealStatusPayload {

    @Schema(description = "ID сделки")
    @JsonProperty("deal_id")
    private String dealId;

    @Schema(description = "ID статуса сделки")
    @JsonProperty("status_id")
    private String statusId;

}
