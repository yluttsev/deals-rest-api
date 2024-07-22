package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.dealcontractor.ContractorFilterPayload;
import ru.luttsev.deals.model.payload.dealstatus.DealStatusPayload;
import ru.luttsev.deals.model.payload.dealtype.DealTypePayload;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO фильтров для поиска сделок
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Фильтры для поиска сделок")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealFiltersPayload {

    @Schema(description = "ID сделки")
    private String id;

    @Schema(description = "Описание сделки")
    private String description;

    @Schema(description = "Номер договора")
    @JsonProperty("agreement_number")
    private String agreementNumber;

    @Schema(description = "Начальная дата вступления соглашений в силу")
    @JsonProperty("agreement_date_from")
    private LocalDate agreementDateFrom;

    @Schema(description = "Конечная дата вступления соглашений в силу")
    @JsonProperty("agreement_date_to")
    private LocalDate agreementDateTo;

    @Schema(description = "Начальная дата срока действия сделки")
    @JsonProperty("availability_date_from")
    private LocalDate availabilityDateFrom;

    @Schema(description = "Конечная дата срока действия сделки")
    @JsonProperty("availability_date_to")
    private LocalDate availabilityDateTo;

    @Schema(description = "Типы сделки")
    private List<DealTypePayload> type;

    @Schema(description = "Статусы сделки")
    private List<DealStatusPayload> status;

    @Schema(description = "Начальная дата закрытия сделки")
    @JsonProperty("close_dt_from")
    private LocalDate closeDateFrom;

    @Schema(description = "Конечная дата закрытия сделки")
    @JsonProperty("close_dt_to")
    private LocalDate closeDateTo;

    @Schema(description = "Фильтр по заемщику сделки")
    private ContractorFilterPayload borrower;

    @Schema(description = "Фильтр по поручителю сделки")
    private ContractorFilterPayload warranity;

}
