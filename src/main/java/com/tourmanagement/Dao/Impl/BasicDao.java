package com.tourmanagement.Dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

abstract class BasicDao {
    @PersistenceContext
    protected EntityManager _entityManager;
}
