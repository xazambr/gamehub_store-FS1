package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.repositories.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private OrdersRepository repository;
    @InjectMocks private OrdenesServiceImpl service;
}
