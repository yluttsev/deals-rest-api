package ru.luttsev.deals.model.payload.deal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO страницы со сделками
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Страница найденных по фильтрам сделок")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealPagePayload {

    @Schema(description = "Номер страницы")
    private int page;

    @Schema(description = "Количество элементов на странице")
    private int items;

    private List<DealPayload> deals;

}
