package com.dev.superapp.dao.super_app;

import com.dev.superapp.model.super_app.Apps;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends CrudRepository<Apps,Long> {
}