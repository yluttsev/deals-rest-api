package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.dealstatus.DealStatusPayload;
import ru.luttsev.deals.model.payload.dealtype.DealTypePayload;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO для сохранения сделки
 *
 * @author Yuri Luttsev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveDealPayload {

    private String id;

    private String description;

    @JsonProperty("agreement_number")
    private String agreementNumber;

    @JsonProperty("agreement_date")
    private LocalDate agreementDate;

    @JsonProperty("agreement_start_dt")
    private LocalDateTime agreementStartDate;

    @JsonProperty("availability_date")
    private LocalDate availabilityDate;

    private DealTypePayload type;

    private DealStatusPayload status;

    private BigDecimal sum;

    @JsonProperty("close_dt")
    private LocalDateTime closeDate;

}
