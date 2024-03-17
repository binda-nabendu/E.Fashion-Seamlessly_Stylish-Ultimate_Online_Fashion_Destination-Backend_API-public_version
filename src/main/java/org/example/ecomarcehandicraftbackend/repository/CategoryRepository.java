package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String name);
    @Query("Select c from Category c WHERE c.name=:name And c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParent(@Param("name")String name, @Param("parentCategoryName") String parentCategoryName);
}
