package com._3stock.service;

import com._3stock.module.vo.Data;
import com._3stock.module.vo.GoPicture;

import java.util.List;

public interface IStock {
    List<Data> queryPresetStockData(List<String> gids);

    public GoPicture queryGidGoPicture(String gid);
}
