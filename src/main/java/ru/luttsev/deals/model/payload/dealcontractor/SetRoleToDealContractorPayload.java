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
public class SetRoleToDealContractorPayload {

    @JsonProperty("contractor_id")
    private String contractorId;

    @JsonProperty("role_id")
    private String roleId;

}
