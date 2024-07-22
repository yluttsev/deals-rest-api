package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.entity.DealType;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO сделки
 *
 * @author Yuri Luttsev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealPayload {

    private String id;

    private String description;

    @JsonProperty("agreement_number")
    private String agreementNumber;

    @JsonProperty("agreement_date")
    private LocalDateTime agreementDate;

    @JsonProperty("agreement_start_dt")
    private LocalDateTime agreementStartDate;

    @JsonProperty("availability_date")
    private LocalDateTime availabilityDate;

    private DealType type;

    private DealStatus status;

    private BigDecimal sum;

    @JsonProperty("close_dt")
    private LocalDateTime closeDate;

    private List<DealContractorPayload> contractors;

}
