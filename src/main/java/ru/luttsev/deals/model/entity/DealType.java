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
@Table(name = "deal_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLRestriction("is_active = true")
public class DealType {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
