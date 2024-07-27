package ru.luttsev.deals.model.payload.dealcontractor;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.luttsev.deals.model.entity.ContractorRole;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.entity.DealType;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс для создания спецификации по поиску сделок
 *
 * @author Yuri Luttsev
 */
public final class DealSpecification {

    private DealSpecification() {
    }

    /**
     * Создает спецификацию поиска сделок по переданным фильтрам
     *
     * @param filters {@link DealFiltersPayload фильтры поиска сделок}
     * @return {@link Specification спецификация} поиска сделок
     */
    public static Specification<Deal> getSpecification(DealFiltersPayload filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));

            if (filters.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filters.getId()));
            }
            if (filters.getDescription() != null) {
                predicates.add(criteriaBuilder.equal(root.get("description"), filters.getDescription()));
            }
            if (filters.getAgreementNumber() != null) {
                predicates.add(criteriaBuilder.like(root.get("agreementNumber"), filters.getAgreementNumber()));
            }
            if (filters.getAgreementDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("agreementDate"), filters.getAgreementDateFrom()));
            }
            if (filters.getAgreementDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("agreementDate"), filters.getAgreementDateTo()));
            }
            if (filters.getAvailabilityDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("availabilityDate"), filters.getAvailabilityDateFrom()));
            }
            if (filters.getAvailabilityDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("availabilityDate"), filters.getAvailabilityDateTo()));
            }
            if (filters.getType() != null && !filters.getType().isEmpty()) {
                filters.getType().forEach(dealType -> predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.<DealType>get("type").<String>get("id"), dealType.getId())
                        )
                ));
            }
            if (filters.getCloseDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("closeDate"), filters.getCloseDateFrom()));
            }
            if (filters.getCloseDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("closeDate"), filters.getCloseDateTo()));
            }
            if (filters.getBorrower() != null) {
                return createContractorPredicate(root, criteriaBuilder, filters.getBorrower());
            }
            if (filters.getWarranity() != null) {
                return createContractorPredicate(root, criteriaBuilder, filters.getWarranity());
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static Predicate createContractorPredicate(Root<Deal> root,
                                                       CriteriaBuilder criteriaBuilder,
                                                       ContractorFilterPayload filter) {
        Join<Deal, DealContractor> dealContractors = root.join("contractors");
        Join<DealContractor, ContractorRole> contractorRoles = dealContractors.join("roles");
        List<Predicate> contractorPredicates = new ArrayList<>(List.of(
                criteriaBuilder.equal(dealContractors.<UUID>get("id"), UUID.fromString(filter.getId())),
                criteriaBuilder.like(dealContractors.get("name"), filter.getName()),
                criteriaBuilder.like(dealContractors.get("inn"), filter.getInn())
        ));
        Predicate rolePredicate = criteriaBuilder.or(
                criteriaBuilder.equal(contractorRoles.get("category"), "BORROWER"),
                criteriaBuilder.equal(contractorRoles.get("category"), "WARRANTY")
        );

        return criteriaBuilder.and(rolePredicate, criteriaBuilder.or(contractorPredicates.toArray(Predicate[]::new)));
    }

}
