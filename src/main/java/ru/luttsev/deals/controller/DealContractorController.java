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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SaveDealContractorPayload;
import ru.luttsev.deals.service.DealContractorService;
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.WebAuditLog;

import java.util.UUID;

/**
 * Контроллер для работы с контрагентами сделки
 *
 * @author Yuri Luttsev
 */
@Tag(name = "deal-contractor", description = "API для работы с контрагентами сделки")
@RestController
@RequestMapping("/deal-contractor")
@RequiredArgsConstructor
public class DealContractorController {

    /**
     * {@link DealContractorService Сервис для работы с контрагентами сделки}
     */
    private final DealContractorService dealContractorService;

    /**
     * {@link ModelMapper Маппер сущностей и DTO}
     */
    private final ModelMapper mapper;

    /**
     * Создает нового или обновляет существующего контрагента сделки
     *
     * @param payload {@link SaveDealContractorPayload DTO сохранения контрагента сделки}
     * @return {@link DealContractorPayload DTO} созданного или обновленного контрагента сделки
     */
    @Operation(summary = "Сохранение контрагента сделки", description = "Создание нового контрагента, если не передан ID, " +
            "в противном случае обновление контрагента с переданным ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное создание/обновление контрагента сделки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DealContractorPayload.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрагент с переданным ID не найден",
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
    @WebAuditLog(logLevel = LogLevel.INFO)
    @PutMapping("/save")
    public DealContractorPayload saveDealContractor(@RequestBody SaveDealContractorPayload payload) {
        DealContractor dealContractor = mapper.map(payload, DealContractor.class);
        DealContractor savedDealContractor = dealContractorService.save(dealContractor);
        return mapper.map(savedDealContractor, DealContractorPayload.class);
    }

    /**
     * Удаляет контрагента сделки по ID
     *
     * @param dealContractorId ID контрагента сделки
     * @return {@link ResponseEntity} пустой ответ
     */
    @Operation(summary = "Удаление контрагента сделки по ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление контрагента сделки по ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Контрагент с указанным ID не найден",
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
    @WebAuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDealContractorById(
            @Parameter(name = "id",
                    description = "ID контрагента сделки",
                    required = true
            )
            @PathVariable("id") String dealContractorId) {
        dealContractorService.deleteById(UUID.fromString(dealContractorId));
        return ResponseEntity.ok().build();
    }

}
