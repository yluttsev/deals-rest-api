package ru.luttsev.deals.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;

import java.util.Collection;

public interface SecurityService {

    boolean checkRole(String role, Collection<? extends GrantedAuthority> roles);

    void updateFiltersWithRole(DealFiltersPayload filters, UserDetails userDetails);

}
