package com.duoc.msvc_product.services;

import com.duoc.msvc_product.clients.CategoryClient;
import com.duoc.msvc_product.exceptions.ProductExceptions;
import com.duoc.msvc_product.models.Producto;
import com.duoc.msvc_product.models.dtos.ExtCategoriaDTO;
import com.duoc.msvc_product.repositories.ProductRepository;
import feign.FeignException;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private CategoryClient categoryClient;

    private Producto testProduct;
    private List<Producto> productList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.testProduct = new Producto();
        this.testProduct.setId(1L);
        this.testProduct.setNombre("Nombre");
        this.testProduct.setMarca("Marca");
        this.testProduct.setModelo("Modelo");
        this.testProduct.setDescripcion("Descripcion");
        this.testProduct.setPrecio(500000);
        this.testProduct.setCategoriaId(7L);
        this.testProduct.setEstado("ACTIVO");

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 50; i++) {
            Producto producto = new Producto();
            producto.setId((long) i);
            producto.setNombre(faker.device().modelName());
            producto.setMarca(faker.device().manufacturer());
            producto.setModelo(faker.bothify("ABC-####"));
            producto.setDescripcion(faker.lorem().paragraph());
            producto.setPrecio(faker.number().numberBetween(1000,1000000));
            producto.setCategoriaId((7L));
            productList.add(producto);
        }
    }

    @Test
    @DisplayName("Deberia listar todos los productos")
    public void shouldListAllProducts() {
        when(this.productRepository.findAll()).thenReturn(List.of(this.testProduct));

        ExtCategoriaDTO categoriaDTO = new ExtCategoriaDTO();
        categoriaDTO.setId(7L);
        categoriaDTO.setNombre("Categoria 7");
        categoriaDTO.setDescripcion("prueba de categoria");
        categoriaDTO.setEstado("ACTIVO");

        List<Producto> result = this.productService.findAll();

        assertThat(result).hasSize(1);
        Producto producto = result.get(0);
        assertThat(producto.getId()).isEqualTo(1L);
        assertThat(producto.getNombre()).isEqualTo("Nombre");
        assertThat(producto.getDescripcion()).isEqualTo("Descripcion");
        assertThat(producto.getEstado()).isEqualTo("ACTIVO");
        verify(productRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deberia guardar un producto cuando la categoria existe y esta activa")
    void shouldSaveProductWhenCategoryExistsAndIsActive() {

        ExtCategoriaDTO categoriaDTO = new ExtCategoriaDTO();
        categoriaDTO.setId(7L);
        categoriaDTO.setNombre("Categoria 7");
        categoriaDTO.setEstado("ACTIVO");

        when(categoryClient.findById(7L))
                .thenReturn(categoriaDTO);

        when(productRepository.save(testProduct))
                .thenReturn(testProduct);

        Producto result = productService.save(testProduct);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProduct.getId());
        assertThat(result.getNombre()).isEqualTo(testProduct.getNombre());

        verify(categoryClient, times(1)).findById(7L);
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando la categoria esta desactivada")
    void shouldThrowExceptionWhenCategoryIsDisabled() {

        ExtCategoriaDTO categoriaDTO = new ExtCategoriaDTO();
        categoriaDTO.setId(7L);
        categoriaDTO.setNombre("Categoria 7");
        categoriaDTO.setEstado("DESACTIVADO");

        when(categoryClient.findById(7L))
                .thenReturn(categoriaDTO);

        assertThatThrownBy(() -> productService.save(testProduct))
                .isInstanceOf(ProductExceptions.class)
                .hasMessage("La categoría asociada se encuentra inactiva");

        verify(categoryClient, times(1)).findById(7L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deberia lanzar excepcion cuando la categoria no existe")
    void shouldThrowExceptionWhenCategoryDoesNotExist() {

        FeignException.NotFound notFoundException =
                mock(FeignException.NotFound.class);

        when(categoryClient.findById(7L))
                .thenThrow(notFoundException);

        assertThatThrownBy(() -> productService.save(testProduct))
                .isInstanceOf(ProductExceptions.class)
                .hasMessage("La categoría con ID 7 no existe en el sistema.");

        verify(categoryClient, times(1)).findById(7L);
        verify(productRepository, never()).save(any());
    }


}
