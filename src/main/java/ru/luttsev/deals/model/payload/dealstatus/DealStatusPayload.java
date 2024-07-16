package ru.luttsev.deals.model.payload.dealstatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealStatusPayload {

    private String id;

    private String name;

}
