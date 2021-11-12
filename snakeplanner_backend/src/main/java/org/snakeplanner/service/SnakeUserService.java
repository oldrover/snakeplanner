package org.snakeplanner.service;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.snakeplanner.dao.SnakeUserDao;
import org.snakeplanner.entity.SnakeUser;

@ApplicationScoped
public class SnakeUserService {

    @Inject SnakeUserDao snakeUserDao;

    
    public void saveUser(SnakeUser user) {        
        snakeUserDao.update(user);
    }

    public SnakeUser getUserById(UUID id) {        
        return snakeUserDao.findById(id);
    }
    
}
