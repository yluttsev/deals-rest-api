package ru.luttsev.deals.model.payload.dealstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO статуса сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Статус сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealStatusPayload {

    @Schema(description = "ID статуса сделки")
    private String id;

    @Schema(description = "Название статуса сделки")
    private String name;

}
