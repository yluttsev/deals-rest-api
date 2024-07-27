package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealstatus.DealStatusPayload;
import ru.luttsev.deals.model.payload.dealtype.DealTypePayload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO сделки
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Сделка")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealPayload {

    @Schema(description = "ID сделки")
    private String id;

    @Schema(description = "Описание сделки")
    private String description;

    @Schema(description = "Номер договора сделки")
    @JsonProperty("agreement_number")
    private String agreementNumber;

    @Schema(description = "Дата договора сделки")
    @JsonProperty("agreement_date")
    private LocalDateTime agreementDate;

    @Schema(description = "Дата вступления соглашения в силу")
    @JsonProperty("agreement_start_dt")
    private LocalDateTime agreementStartDate;

    @Schema(description = "Срок действия сделки")
    @JsonProperty("availability_date")
    private LocalDateTime availabilityDate;

    @Schema(description = "Тип сделки")
    private DealTypePayload type;

    @Schema(description = "Статус сделки")
    private DealStatusPayload status;

    @Schema(description = "Сумма сделки")
    private BigDecimal sum;

    @Schema(description = "Дата закрытия сделки")
    @JsonProperty("close_dt")
    private LocalDateTime closeDate;

    @Schema(description = "Список контрагентов сделки")
    private List<DealContractorPayload> contractors;

}
