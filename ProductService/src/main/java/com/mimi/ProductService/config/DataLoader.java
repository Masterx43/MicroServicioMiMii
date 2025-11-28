package com.mimi.ProductService.config;

import com.mimi.ProductService.model.Category;
import com.mimi.ProductService.model.Product;
import com.mimi.ProductService.repository.CategoryRepository;
import com.mimi.ProductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    @Override
    public void run(String... args) throws Exception {

        if (productRepo.count() > 0) return ;

        // Crear categorías
        Category tratamiento = categoryRepo.save(Category.builder().nombre("Tratamiento").build());
        Category cuidadoDiario = categoryRepo.save(Category.builder().nombre("Cuidado Diario").build());
        Category styling = categoryRepo.save(Category.builder().nombre("Styling").build());
        Category accesorios = categoryRepo.save(Category.builder().nombre("Accesorios").build());
        Category coloracion = categoryRepo.save(Category.builder().nombre("Coloración").build());
        Category maquillaje = categoryRepo.save(Category.builder().nombre("Maquillaje").build());

        // ------- PRODUCTOS PRE-CARGADOS -------

        productRepo.save(Product.builder()
                .nombre("Mascarilla Capilar Hidratante")
                .descripcion("Nutre tu cabello con extractos naturales y brillo duradero. Ideal para cabellos secos o dañados.")
                .precio(12990)
                .imagen("productos/mascarilla_capilar.webp")
                .categoria(tratamiento)
                .build());

        productRepo.save(Product.builder()
                .nombre("Serum Capilar de Keratina")
                .descripcion("Repara las puntas abiertas y protege del calor de planchas y secadores.")
                .precio(18990)
                .imagen("productos/serum_capilar.webp")
                .categoria(cuidadoDiario)
                .build());

        productRepo.save(Product.builder()
                .nombre("TermoProtector Profesional")
                .descripcion("Protege tu cabello hasta 230°C, evitando quiebre y pérdida de hidratación.")
                .precio(15990)
                .imagen("productos/termoprotector.webp")
                .categoria(styling)
                .build());

        productRepo.save(Product.builder()
                .nombre("Crema de Peinar para Rizos")
                .descripcion("Define tus rizos y controla el frizz sin dejar residuos. Enriquecida con aceites naturales.")
                .precio(10990)
                .imagen("productos/crema_de_peinar_rizos.webp")
                .categoria(styling)
                .build());

        productRepo.save(Product.builder()
                .nombre("Cepillo Desenredante Profesional")
                .descripcion("Cerdas flexibles que desenredan sin dolor. Ideal para todo tipo de cabello.")
                .precio(4590)
                .imagen("productos/cepillo_desenredante.webp")
                .categoria(accesorios)
                .build());

        productRepo.save(Product.builder()
                .nombre("Shampoo con Keratina y Argán")
                .descripcion("Limpieza profunda y nutrición intensa, sin sulfatos ni parabenos.")
                .precio(9990)
                .imagen("productos/shampoo_keratina.webp")
                .categoria(cuidadoDiario)
                .build());

        productRepo.save(Product.builder()
                .nombre("Crema de Masaje Capilar 500ml")
                .descripcion("Revitaliza el cuero cabelludo y estimula el crecimiento. Ideal para uso profesional.")
                .precio(11990)
                .imagen("productos/crema_masaja_capilar_500ml.webp")
                .categoria(tratamiento)
                .build());

        productRepo.save(Product.builder()
                .nombre("Espejo Doble con Aumento x10")
                .descripcion("Espejo portátil con aumento ideal para maquillaje y cuidado facial de precisión.")
                .precio(8490)
                .imagen("productos/espejo_doble_x10.webp")
                .categoria(accesorios)
                .build());

        productRepo.save(Product.builder()
                .nombre("Oxidante Vol. 30 - 75ml")
                .descripcion("Formulación estable y cremosa para mezclas de coloración profesional.")
                .precio(4990)
                .imagen("productos/oxidante_vol30.webp")
                .categoria(coloracion)
                .build());

        productRepo.save(Product.builder()
                .nombre("Pestañas Individuales Negras")
                .descripcion("Pestañas ligeras y naturales, perfectas para un acabado personalizado.")
                .precio(6990)
                .imagen("productos/pestanias_individual_negra.webp")
                .categoria(maquillaje)
                .build());

        productRepo.save(Product.builder()
                .nombre("Pestañas Lily Natural Look")
                .descripcion("Diseño liviano y curvado, aporta volumen sin perder naturalidad.")
                .precio(7490)
                .imagen("productos/pestanias_lily.webp")
                .categoria(maquillaje)
                .build());

        productRepo.save(Product.builder()
                .nombre("Pinza Profesional para Cejas")
                .descripcion("Punta precisa y acero inoxidable. Ideal para definir cejas con máxima precisión.")
                .precio(4990)
                .imagen("productos/pinza_cejas.webp")
                .categoria(accesorios)
                .build());

        productRepo.save(Product.builder()
                .nombre("Tintura Capilar Modastyling")
                .descripcion("Color vibrante y duradero con fórmula protectora del cabello.")
                .precio(9990)
                .imagen("productos/tintura_modastyling.webp")
                .categoria(coloracion)
                .build());

        System.out.println("Productos precargados correctamente ");
    }
}
