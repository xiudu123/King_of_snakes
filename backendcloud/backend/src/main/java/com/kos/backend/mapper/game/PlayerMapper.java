package com.kos.backend.mapper.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kos.backend.pojo.game.Player;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerMapper extends BaseMapper<Player> {
}
