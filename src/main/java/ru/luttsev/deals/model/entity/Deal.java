package ru.luttsev.deals.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLRestriction("is_active = true")
public class Deal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "agreement_number")
    private String agreementNumber;

    @Column(name = "agreement_date")
    @Temporal(TemporalType.DATE)
    private LocalDate agreementDate;

    @Column(name = "agreement_start_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime agreementStartDate;

    @Column(name = "availability_date")
    @Temporal(TemporalType.DATE)
    private LocalDate availabilityDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id")
    private DealType type;

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
    private DealStatus status;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "close_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closeDate;

    @OneToMany(mappedBy = "deal")
    private List<DealContractor> contractors;

    @CreatedDate
    @Column(name = "create_date", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifyDate;

    @CreatedBy
    @Column(name = "create_user_id")
    private String createUserId;

    @LastModifiedBy
    @Column(name = "modify_user_id")
    private String modifyUserId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void setDefaultValues() {
        status = DealStatus.builder()
                .id("DRAFT")
                .name("Черновик")
                .isActive(true)
                .build();
    }

}
