package org.uniquindio.prr23.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.uniquindio.prr23.dto.UserRegistrationRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<UserRegistrationRequest> users = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public UserRegistrationRequest createUser(UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationRequest createdUser = new UserRegistrationRequest(
                nextId.getAndIncrement(),
                userRegistrationRequest.email(),
                userRegistrationRequest.password(),
                userRegistrationRequest.fullName(),
                userRegistrationRequest.dateBirth(),
                userRegistrationRequest.rol()
        );
        users.add(createdUser);
        return createdUser;
    }

    public Optional<UserRegistrationRequest> getUserById(Long id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    public List<UserRegistrationRequest> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Metodo para la paginacion/pagination
    public Page<UserRegistrationRequest> getAllUsers(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserRegistrationRequest> list;

        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, pageable, users.size());
    }

    public Optional<UserRegistrationRequest> updateUser(Long id, UserRegistrationRequest userRegistrationRequest) {
        return getUserById(id).map(existingUser -> {
            users.remove(existingUser);
            UserRegistrationRequest updatedUser = new UserRegistrationRequest(
                    id,
                    userRegistrationRequest.email(),
                    userRegistrationRequest.password(),
                    userRegistrationRequest.fullName(),
                    userRegistrationRequest.dateBirth(),
                    userRegistrationRequest.rol()
            );
            users.add(updatedUser);
            return updatedUser;
        });
    }

    public boolean deleteUser(Long id) {
        return getUserById(id).map(users::remove).orElse(false);
    }
}