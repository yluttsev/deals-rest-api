package ru.luttsev.deals.model.payload.dealtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealTypePayload {

    private String id;

    private String name;

}
