package com.duoc.msvc_category.services;

import com.duoc.msvc_category.exceptions.CategoryExceptions;
import com.duoc.msvc_category.models.Categoria;
import com.duoc.msvc_category.repositories.CategoryRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Categoria categoriaPrueba;
    private List<Categoria> categoriaList = new  ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.categoriaPrueba = new Categoria();
        this.categoriaPrueba.setId(1L);
        this.categoriaPrueba.setNombre("Categoria Prueba");
        this.categoriaPrueba.setDescripcion("Categoria Prueba");
        this.categoriaPrueba.setEstado("ACTIVO");

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 50; i++) {
            Categoria categoria = new Categoria();
            categoria.setId((long) i);
            categoria.setNombre(faker.commerce().department());
            categoria.setDescripcion(faker.lorem().sentence());
            categoria.setEstado("ACTIVO");

            categoriaList.add(categoria);

        }

    }

    @Test
    @DisplayName("Deberia listar todas las categorias")
    public void ShouldListAllCategories() {

        // Arrange
        List<Categoria> categorias = this.categoriaList;
        categorias.add(this.categoriaPrueba);
        when(this.categoryRepository.findAll()).thenReturn(categorias);

        // ACT
        List<Categoria> result = this.categoryService.findAll();

        // ASSERT
        assertThat(result).hasSize(51);
        assertThat(result).contains(categoriaPrueba);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deberia buscar una categoria usando el id")
    public void ShouldFindCategoryById() {

        // ARRANGE
        Long id = 1L;
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(this.categoriaPrueba));

        // ACT
        Categoria result = this.categoryService.findById(id);

        //  ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Categoria Prueba");
        assertThat(result.getDescripcion()).isEqualTo("Categoria Prueba");
        assertThat(result.getEstado()).isEqualTo("ACTIVO");
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deberia buscar una categoria con id inexistente")
    public void ShouldNotFindCategoryById() {

        // ARRANGE
        Long id = 130L;
        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());


        // ASSERT
        assertThatThrownBy(() -> {
            this.categoryService.findById(id);
        }).isInstanceOf(CategoryExceptions.class).hasMessage("La categoria de id: " + id + " no existe");
        verify(categoryRepository, times(1)).findById(id);
        }

    @Test
    @DisplayName("Deberia guardar una categoria nueva")
    public void ShouldSaveCategory() {

        // ARRANGE
        when(this.categoryRepository.save(this.categoriaPrueba)).thenReturn(this.categoriaPrueba);

        // ACT
        Categoria result =  this.categoryService.save(this.categoriaPrueba);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Categoria Prueba");
        verify(categoryRepository, times(1)).save(this.categoriaPrueba);

    }

    @Test
    @DisplayName("Deberia Actualizar una categoria existente")
    public void ShouldUpdateCategory() {

        // ARRANGE
        Long id = 1L;
        Categoria cambios = new Categoria();
        cambios.setNombre("Categoria nombre Actualizada");
        cambios.setDescripcion("Categoria descripcion Actualizada");
        cambios.setEstado("ACTIVO");
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(this.categoriaPrueba));
        when(this.categoryRepository.save(any(Categoria.class))).thenAnswer(inv -> inv.getArgument(0));

        // ACT
        Categoria result = this.categoryService.updateById(id, cambios);

        // ASSERT
        assertThat(result.getNombre()).isEqualTo("Categoria nombre Actualizada");
        assertThat(result.getDescripcion()).isEqualTo("Categoria descripcion Actualizada");
        assertThat(result.getEstado()).isEqualTo("ACTIVO");
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).save(this.categoriaPrueba);

    }

    @Test
    @DisplayName("Debe lanzar excepcion al actualizar categoria inexistente")
    public void ShouldNotUpdateCategory() {

        // ARRANGE
        Long id = 999L;
        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());

        // ASSERT

        assertThatThrownBy(() -> {
            this.categoryService.updateById(id, this.categoriaPrueba);
        }).isInstanceOf(CategoryExceptions.class)
                .hasMessage("La categoria con id: " + id + " no existe");
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, never()).save(any(Categoria.class));
    }

    @Test
    @DisplayName("Deberia eliminar una categoria")
    public void ShouldDeleteCategory() {

        // ARRANGE
        Long id = 1L;

        // ACT
        this.categoryService.deleteById(id);

        // ASSERT
        verify(categoryRepository, times(1)).deleteById(id);

    }
}
