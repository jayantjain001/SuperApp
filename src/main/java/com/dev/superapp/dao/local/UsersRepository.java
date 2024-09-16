package com.dev.superapp.dao.local;

import com.dev.superapp.model.local.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {
}