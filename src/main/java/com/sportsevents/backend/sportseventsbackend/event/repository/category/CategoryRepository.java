package com.sportsevents.backend.sportseventsbackend.event.repository.category;

import com.sportsevents.backend.sportseventsbackend.event.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
