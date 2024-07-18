package ru.luttsev.deals.model.payload.dealcontractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorFilterPayload {

    private String id;

    private String name;

    private String inn;

}
