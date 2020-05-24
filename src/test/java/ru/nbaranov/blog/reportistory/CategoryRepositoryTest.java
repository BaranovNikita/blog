package ru.nbaranov.blog.reportistory;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nbaranov.blog.entity.Category;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindByTitle_thenReturnCategory() {
        var category = new Category();
        category.setTitle("category");
        entityManager.persist(category);
        entityManager.flush();

        var found = categoryRepository.findByTitle(category.getTitle());

        assertThat(found.getTitle())
                .isEqualTo(category.getTitle());
    }

    @Test()
    public void whenCreateWithUniqueTitle_thenCorrectException() {
        var uniqueCategory = new Category();
        uniqueCategory.setTitle("category");
        entityManager.persist(uniqueCategory);

        var notUniqueCategory = new Category();
        notUniqueCategory.setTitle("category");

        Exception exception = assertThrows(PersistenceException.class, () -> {
            entityManager.persist(notUniqueCategory);
            entityManager.flush();
        });

        assertTrue(exception.getCause() instanceof ConstraintViolationException);
    }
}