package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.dealcontractor.ContractorFilterPayload;
import ru.luttsev.deals.model.payload.dealstatus.DealStatusPayload;
import ru.luttsev.deals.model.payload.dealtype.DealTypePayload;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealFiltersPayload {

    private String id;

    private String description;

    @JsonProperty("agreement_number")
    private String agreementNumber;

    @JsonProperty("agreement_date_from")
    private LocalDate agreementDateFrom;

    @JsonProperty("agreement_date_to")
    private LocalDate agreementDateTo;

    @JsonProperty("availability_date_from")
    private LocalDate availabilityDateFrom;

    @JsonProperty("availability_date_to")
    private LocalDate availabilityDateTo;

    private List<DealTypePayload> type;

    private List<DealStatusPayload> status;

    @JsonProperty("close_dt_from")
    private LocalDate closeDateFrom;

    @JsonProperty("close_dt_to")
    private LocalDate closeDateTo;

    private ContractorFilterPayload borrower;

    private ContractorFilterPayload warranity;

}
