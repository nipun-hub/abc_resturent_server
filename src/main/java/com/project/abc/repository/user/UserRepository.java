package com.project.abc.repository.user;

import com.project.abc.model.order.Order;
import com.project.abc.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatusNot(String email, User.UserStatus status);

    Optional<User> findByPhoneAndStatusNot(String phone, User.UserStatus status);

    @Query("SELECT u FROM user u WHERE (:fullName IS NULL OR u.fullName LIKE %:fullName%) " +
            "AND (:email IS NULL OR u.email LIKE %:email%) " +
            "AND (:status IS NULL OR u.status = :status) " +
            "AND (:role IS NULL OR u.role = :role)")
    Page<User> findAllUsers(
            @Param("fullName") String fullName,
            @Param("email") String email,
            @Param("status") User.UserStatus status,
            @Param("role") User.UserRole role,
            Pageable pageable
    );

    int countByRole(User.UserRole role);
}
