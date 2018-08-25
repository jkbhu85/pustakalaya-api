package com.jk.ptk.f.bookcategory;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.f.book.BookCategory;

@RestController
@RequestMapping("/ptk/book/category")
public class BookCategoryController {

	@GetMapping
	public List<BookCategory> getAll() {
		return Arrays.asList(BookCategory.categories);
	}
}
