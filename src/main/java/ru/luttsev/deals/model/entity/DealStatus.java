package ru.luttsev.deals.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "deal_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLRestriction("is_active = true")
public class DealStatus {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;

}
