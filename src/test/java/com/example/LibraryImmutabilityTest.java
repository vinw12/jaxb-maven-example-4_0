package com.example;

import com.example.library.Book;
import com.example.library.Library;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the JAXB-generated classes are immutable as required by the
 * immutable-xjc plugin configuration in pom.xml.
 *
 * <p>Immutability guarantees enforced by immutable-xjc:
 * <ul>
 *   <li>No public setter methods</li>
 *   <li>All declared fields are {@code final}</li>
 *   <li>Classes themselves are {@code final}</li>
 *   <li>Builder utility classes are generated for convenient construction</li>
 * </ul>
 */
class LibraryImmutabilityTest {

    // -----------------------------------------------------------------------
    // Book – no setters
    // -----------------------------------------------------------------------

    @Test
    void book_hasNoSetterMethods() {
        List<String> setters = Arrays.stream(Book.class.getMethods())
                .map(Method::getName)
                .filter(name -> name.startsWith("set"))
                .toList();

        assertTrue(setters.isEmpty(),
                "Book should have no setter methods, but found: " + setters);
    }

    // -----------------------------------------------------------------------
    // Book – all fields final
    // -----------------------------------------------------------------------

    @Test
    void book_allDeclaredFieldsAreFinal() {
        for (Field field : Book.class.getDeclaredFields()) {
            assertTrue(Modifier.isFinal(field.getModifiers()),
                    "Field '" + field.getName() + "' in Book should be final");
        }
    }

    // -----------------------------------------------------------------------
    // Book – class is final
    // -----------------------------------------------------------------------

    @Test
    void book_classIsFinal() {
        assertTrue(Modifier.isFinal(Book.class.getModifiers()),
                "Book class should be final");
    }

    // -----------------------------------------------------------------------
    // Book – builder creates a usable instance
    // -----------------------------------------------------------------------

    @Test
    void book_builderCreatesCorrectInstance() {
        Book book = Book.builder()
                .withTitle("Effective Java")
                .withAuthor("Joshua Bloch")
                .withIsbn("978-0134685991")
                .withYear(2018)
                .build();

        assertEquals("Effective Java",      book.getTitle());
        assertEquals("Joshua Bloch",        book.getAuthor());
        assertEquals("978-0134685991",      book.getIsbn());
        assertEquals(2018,                  book.getYear());
    }

    // -----------------------------------------------------------------------
    // Library – no setters
    // -----------------------------------------------------------------------

    @Test
    void library_hasNoSetterMethods() {
        List<String> setters = Arrays.stream(Library.class.getMethods())
                .map(Method::getName)
                .filter(name -> name.startsWith("set"))
                .toList();

        assertTrue(setters.isEmpty(),
                "Library should have no setter methods, but found: " + setters);
    }

    // -----------------------------------------------------------------------
    // Library – all fields final
    // -----------------------------------------------------------------------

    @Test
    void library_allDeclaredFieldsAreFinal() {
        for (Field field : Library.class.getDeclaredFields()) {
            assertTrue(Modifier.isFinal(field.getModifiers()),
                    "Field '" + field.getName() + "' in Library should be final");
        }
    }

    // -----------------------------------------------------------------------
    // Library – class is final
    // -----------------------------------------------------------------------

    @Test
    void library_classIsFinal() {
        assertTrue(Modifier.isFinal(Library.class.getModifiers()),
                "Library class should be final");
    }

    // -----------------------------------------------------------------------
    // Library – builder creates a usable instance with books
    // -----------------------------------------------------------------------

    @Test
    void library_builderCreatesCorrectInstance() {
        Book book = Book.builder()
                .withTitle("Clean Code")
                .withAuthor("Robert C. Martin")
                .withIsbn("978-0132350884")
                .withYear(2008)
                .build();

        Library library = Library.builder()
                .withName("My Library")
                .addBook(book)
                .build();

        assertEquals("My Library", library.getName());
        assertEquals(1,            library.getBook().size());
        assertEquals("Clean Code", library.getBook().get(0).getTitle());
    }
}
