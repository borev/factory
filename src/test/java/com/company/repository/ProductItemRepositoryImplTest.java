package com.company.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static com.company.CommonData.newProductItem;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ProductItemRepositoryImplTest {

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ProductItemRepositoryImpl productItemRepository;

    @BeforeEach
    public void init() {
        var items = new ArrayList<>(asList(
                newProductItem(1L, 1L, 1L),
                newProductItem(2L, 1L, 1L),
                newProductItem(3L, 1L, 1L)
        ));
        ReflectionTestUtils.setField(productItemRepository, "productItems", items);
    }


    @Test
    public void deleteAll() {
        var res1 = productItemRepository.deleteAll(List.of(newProductItem(1L, 1L, 1L)));
        var res2 = productItemRepository.deleteAll(List.of(
                newProductItem(1L, 1L, 1L),
                newProductItem(2L, 1L, 1L)
        ));

        assertEquals(res1, true);
        assertEquals(res2, false);
    }
}
