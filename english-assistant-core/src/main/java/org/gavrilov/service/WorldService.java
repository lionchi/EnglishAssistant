package org.gavrilov.service;

import org.gavrilov.domain.Category;
import org.gavrilov.domain.User;
import org.gavrilov.domain.World;
import org.gavrilov.dto.WorldDTO;
import org.gavrilov.mappers.MapperFactory;
import org.gavrilov.mappers.WorldMapper;
import org.gavrilov.repository.WorldRepository;
import org.gavrilov.specification.WorldSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class WorldService {
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    private WorldMapper worldMapper = MapperFactory.createMapper(WorldMapper.class);

    @Transactional(readOnly = true)
    public Integer getCountWorldOjUser(User user) {
        return worldRepository.countWorldsByUser(user);
    }

    @Transactional(readOnly = true)
    public List<WorldDTO> getWorlds(User user) {
        return worldRepository.findByUser(user)
                .stream()
                .map(World::getWorldDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<World> isExist(String value, User user) {
        return worldRepository.findOne(WorldSpecification.findByValue(value).and(WorldSpecification.findAllByUser(user)));
    }
    
    public void saveWorld(WorldDTO worldDTO, String userLogin) {
        User user = userService.findUserByLogin(userLogin);
        World worldSave = worldMapper.asWorld(worldDTO);
        worldSave.setUser(user);
        worldSave.setCategory(categoryService.findById(worldDTO.getCategoryId()));
        worldRepository.save(worldSave);
    }

    public boolean deleteWorld(Long id) {
        try {
            worldRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public WorldDTO findById(Long id) {
        World world = worldRepository.findById(id).get();
        return worldMapper.asWorldDTO(world);
    }


    public Page<WorldDTO> getPageOfWorld(User user, Pageable pageable) {
        Page<World> worldPage = worldRepository.findAll(WorldSpecification.findAllByUser(user), pageable);
        return worldPage.map(World::getWorldDTO);
    }

    public void updateWorld(WorldDTO worldDTO) {
        Category category = categoryService.findById(worldDTO.getCategoryId());
        worldRepository.updateWorld(worldDTO.getValue(), worldDTO.getTranslation(), category, worldDTO.getId());
    }
}
