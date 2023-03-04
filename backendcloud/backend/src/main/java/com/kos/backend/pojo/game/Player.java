package com.kos.backend.pojo.game;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer sx;
    private Integer sy;
    private String steps;
    private String increasing;
}
