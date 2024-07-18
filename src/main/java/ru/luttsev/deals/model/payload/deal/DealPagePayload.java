package ru.luttsev.deals.model.payload.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealPagePayload {

    private int page;

    private int items;

    private List<DealPayload> deals;

}
