package ru.luttsev.deals.model.payload.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeDealStatusPayload {

    @JsonProperty("deal_id")
    private String dealId;

    @JsonProperty("status_id")
    private String statusId;

}
