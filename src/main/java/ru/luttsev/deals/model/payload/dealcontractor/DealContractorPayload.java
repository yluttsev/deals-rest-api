package ru.luttsev.deals.model.payload.dealcontractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.luttsev.deals.model.payload.contractorrole.ContractorRolePayload;

import java.util.List;

/**
 * DTO контрагента сделки
 *
 * @author Yuri Luttsev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealContractorPayload {

    private String id;

    private String description;

    @JsonProperty("contractor_id")
    private String contractorId;

    private String name;

    private String inn;

    private boolean main;

    private List<ContractorRolePayload> roles;

}
