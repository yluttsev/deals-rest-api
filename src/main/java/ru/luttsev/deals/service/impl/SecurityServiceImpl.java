package ru.luttsev.deals.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.dealtype.DealTypePayload;
import ru.luttsev.deals.service.SecurityService;

import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean checkRole(String role, Collection<? extends GrantedAuthority> roles) {
        return roles.contains(new SimpleGrantedAuthority(role));
    }

    @Override
    public void updateFiltersWithRole(DealFiltersPayload filters, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        DealTypePayload creditType = DealTypePayload.builder()
                .id("CREDIT")
                .name("Кредитная сделка")
                .build();
        if (!this.checkRole("DEAL_SUPERUSER", roles) || !this.checkRole("SUPERUSER", roles)) {
            if (filters.getType() == null || filters.getType().isEmpty()) {
                filters.setType(List.of(creditType));
            } else {
                if (!filters.getType().contains(creditType)) {
                    filters.setType(List.of(DealTypePayload.builder()
                            .id("null")
                            .name("null")
                            .build()));
                } else {
                    filters.getType().removeIf(type -> !type.getId().equals("CREDIT"));
                }
            }
        }
    }

}
