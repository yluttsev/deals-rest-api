package ru.luttsev.deals.model.payload.contractorrole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO роли контрагента сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Роль контрагента")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorRolePayload {

    @Schema(description = "ID роли")
    private String id;

    @Schema(description = "Название роли")
    private String name;

    @Schema(description = "Категория роли")
    private String category;

}
