package ru.luttsev.deals.model.payload.contractorrole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO роли контрагента сделки
 *
 * @author Yuri Luttsev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractorRolePayload {

    private String id;

    private String name;

    private String category;

}
