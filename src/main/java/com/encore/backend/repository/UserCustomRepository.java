package com.encore.backend.repository;

import java.util.List;

public interface UserCustomRepository {
    boolean deleteUserByEmail(String email);
}
