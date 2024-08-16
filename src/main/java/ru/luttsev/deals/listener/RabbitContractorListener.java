package ru.luttsev.deals.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.rabbitmq.ContractorRabbitMessage;
import ru.luttsev.deals.service.DealContractorService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RabbitContractorListener {

    private final DealContractorService dealContractorService;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "deals_contractor_queue")
    public void getContractorMessages(Message message) throws IOException {
        ContractorRabbitMessage rabbitMessage = objectMapper.readValue(message.getBody(), ContractorRabbitMessage.class);
        List<DealContractor> dealContractors = dealContractorService.getByContractorId(rabbitMessage.getContractor().getContractorId());
        for (DealContractor dc : dealContractors) {
            if (dc.getModifyDate() == null || rabbitMessage.getModifyDate().isAfter(dc.getModifyDate())) {
                dealContractorService.updateByRabbitMessage(rabbitMessage.getContractor(), dc);
            }
        }
    }

}
