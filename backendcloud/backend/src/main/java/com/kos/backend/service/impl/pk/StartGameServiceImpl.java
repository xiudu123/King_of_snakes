package com.kos.backend.service.impl.pk;

import com.kos.backend.consumer.utils.game.GameDouble;
import com.kos.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId) {
        GameDouble.startGame(aId, bId);
        return "start game success";
    }
}
