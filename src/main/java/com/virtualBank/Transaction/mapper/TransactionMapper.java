package com.virtualBank.Transaction.mapper;

import com.virtualBank.Transaction.dto.TransactionRequest;
import com.virtualBank.Transaction.dto.TransactionResponse;
import com.virtualBank.Transaction.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    TransactionEntity toEntity(TransactionRequest req);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    TransactionResponse toResponse(TransactionEntity entity);
}
