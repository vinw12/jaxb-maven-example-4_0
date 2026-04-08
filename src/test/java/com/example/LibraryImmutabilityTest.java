package com.example;

import com.example.library.Book;
import com.example.library.Library;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the properties of the JAXB-generated classes:
 *
 * <ul>
 *   <li><b>Immutability</b> – enforced by immutable-xjc (no setters, final fields
 *       and classes, builder utility classes).</li>
 *   <li><b>Annotation injection</b> – enforced by jaxb-annotate-plugin
 *       ({@code @NotNull} on the required Book fields as declared in
 *       {@code library.xsd} via annox customizations).</li>
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
    // Book – @NotNull annotations injected by jaxb-annotate-plugin
    // -----------------------------------------------------------------------

    @Test
    void book_titleFieldHasNotNullAnnotation() throws NoSuchFieldException {
        Field titleField = Book.class.getDeclaredField("title");
        assertNotNull(titleField.getAnnotation(NotNull.class),
                "Book.title should carry @NotNull injected by jaxb-annotate-plugin");
    }

    @Test
    void book_authorFieldHasNotNullAnnotation() throws NoSuchFieldException {
        Field authorField = Book.class.getDeclaredField("author");
        assertNotNull(authorField.getAnnotation(NotNull.class),
                "Book.author should carry @NotNull injected by jaxb-annotate-plugin");
    }

    @Test
    void book_isbnFieldHasNotNullAnnotation() throws NoSuchFieldException {
        Field isbnField = Book.class.getDeclaredField("isbn");
        assertNotNull(isbnField.getAnnotation(NotNull.class),
                "Book.isbn should carry @NotNull injected by jaxb-annotate-plugin");
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
