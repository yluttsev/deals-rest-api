package ru.luttsev.deals.model.payload.dealcontractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveDealContractorPayload {

    private String id;

    @JsonProperty("deal_id")
    private String dealId;

    @JsonProperty("contractor_id")
    private String contractorId;

    private String name;

    private String inn;

    private boolean main;

}
