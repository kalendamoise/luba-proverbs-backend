package com.focusandcode.rest.webservices.lubaproverbs.proverbs.repository;

import com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
