package com.example.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlackJackBean {

	// 山札
	private ArrayList<Integer> list = new ArrayList<>(Collections.nCopies(52, 0));
	
	//プレイヤー
	private List<Integer> player = new ArrayList<>(); 
	
	//ディーラー
	private List<Integer> dealer = new ArrayList<>();
}
