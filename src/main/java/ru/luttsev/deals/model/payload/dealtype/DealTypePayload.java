package ru.luttsev.deals.model.payload.dealtype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO типа сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Тип сделки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealTypePayload {

    @Schema(description = "ID типа сделки")
    private String id;

    @Schema(description = "Название типа сделки")
    private String name;

}
