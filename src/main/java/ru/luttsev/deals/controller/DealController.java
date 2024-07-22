package ru.luttsev.deals.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.payload.deal.ChangeDealStatusPayload;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;
import ru.luttsev.deals.model.payload.deal.DealPayload;
import ru.luttsev.deals.model.payload.deal.SaveDealPayload;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.AuditLog;

import java.util.UUID;

/**
 * Контроллер для работы со сделками
 *
 * @author Yuri Luttsev
 */
@Tag(name = "deal", description = "API для работы со сделками")
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    /**
     * {@link DealService сервис для работы со сделками}
     */
    private final DealService dealService;

    /**
     * {@link DealStatusService сервис для работы со статусом сделки}
     */
    private final DealStatusService dealStatusService;

    /**
     * {@link ModelMapper Маппер сущностей и DTO}
     */
    private final ModelMapper mapper;

    /**
     * Получение сделки по ID
     *
     * @param dealId ID сделки
     * @return {@link DealPayload DTO сделки}
     */
    @Operation(summary = "Получение сделки по ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение сделки по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealPayload.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сделка с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    }
            )
    })
    @AuditLog(logLevel = LogLevel.INFO)
    @GetMapping("/{id}")
    public DealPayload getDealById(
            @Parameter(name = "id",
                    description = "ID сделки",
                    required = true)
            @PathVariable("id") UUID dealId) {
        Deal deal = dealService.getById(dealId);
        return mapper.map(deal, DealPayload.class);
    }

    /**
     * Создание новой или обновление существующей сделки
     *
     * @param payload {@link SaveDealPayload DTO сохранения сделки}
     * @return {@link DealPayload DTO} созданой или обновленной сделки
     */
    @Operation(summary = "Сохранение сделки", description = "Создание новой сделки, если не передан ID, " +
            "в противном случае обновление сделки с переданным ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание/обновление сдеелки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealPayload.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сделка с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    }
            )
    })
    @AuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public DealPayload saveDeal(@RequestBody SaveDealPayload payload) {
        Deal deal = mapper.map(payload, Deal.class);
        Deal savedDeal = dealService.save(deal);
        return mapper.map(savedDeal, DealPayload.class);
    }

    /**
     * Установка статуса сделки
     *
     * @param payload {@link ChangeDealStatusPayload DTO установки статуса сделки}
     * @return {@link DealPayload DTO сделки} с установленным статусом
     */
    @Operation(summary = "Установка статуса сделки", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная установка статуса сделки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealPayload.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сделка с указанным ID не найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    )
                            )
                    }
            )
    })
    @AuditLog(logLevel = LogLevel.INFO)
    @PatchMapping("/change/status")
    public DealPayload changeDealStatus(@RequestBody ChangeDealStatusPayload payload) {
        Deal deal = dealStatusService.updateStatus(payload.getDealId(), payload.getStatusId());
        return mapper.map(deal, DealPayload.class);
    }

    /**
     * Поиск сделки по переданным фильтрам
     *
     * @param filtersPayload {@link DealFiltersPayload DTO фильтров} для поиска сделки
     * @param page           страница
     * @param contentSize    количество элементов на странице
     * @return {@link DealPagePayload страница с надйенными сделками}
     */
    @Operation(summary = "Поиск сделки по указанным фильтрам", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение найденных сделок по фильтрам",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealPagePayload.class
                                    )
                            )
                    }
            )
    })
    @AuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/search")
    public DealPagePayload searchDeals(@RequestBody DealFiltersPayload filtersPayload,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "contentSize", defaultValue = "10") int contentSize) {
        return dealService.getByFilters(filtersPayload, page, contentSize);
    }

}
