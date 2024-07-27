package ru.luttsev.deals.model.payload.dealcontractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO филтров для поиска контрагентов с определенной ролью
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Фильтры для поиска контрагентов с определенной ролью")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorFilterPayload {

    @Schema(description = "ID контрагента")
    private String id;

    @Schema(description = "Имя контрагента")
    private String name;

    @Schema(description = "ИНН контрагента")
    private String inn;

}
