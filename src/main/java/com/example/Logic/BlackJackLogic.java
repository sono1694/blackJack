package com.example.Logic;

import java.util.List;

public class BlackJackLogic {
	/** カードの種類変換
	 * 
	 * @param cardNumber
	 * @return カード柄
	 */
	public String cardConverter(int cardNumber) {
		switch ((cardNumber -1) / 13) {
		case 0:
			return "♣";
		case 1:
			return "♦";
		case 2:
			return "♡";
		case 3:
			return "♠";
		default:
			return "例外です。";
		}
	}

	/** カードナンバーを1～13に変換
	 * 
	 * @param cardNumber
	 * @return 13で割った余り
	 */
	public int cardNumberConverter(int cardNumber) {
		int pos = cardNumber % 13;
		if (pos == 0) {
			return 13;
		} else {
			return pos;
		}
	}

	/** カードの1/11/12/13をA/J/Q/Kに変換
	 * 
	 * @param cardNumber
	 * @return カードの値
	 */
	public String converterAJQK(int cardNumber) {
		switch (cardNumber) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return Integer.toString(cardNumber);
		}
	}

	/**
	 *  カードがJ/Q/Kの場合、10に変換するメソッド
	 *  
	 * @param cardNumber
	 * @return 変換した値もしくは、そのまま返却
	 */
	public int cardOverConverter(int cardNumber) {
		if (cardNumber > 10) {
			return cardNumber = 10;
		}
		return cardNumber;
	}

	/** 手札バースト確認メソッド
	 * 
	 * @param result
	 * @return true or false
	 */
	public Boolean cardBurst(int result) {
		if (result > 21) {
			System.out.println("手札が21を超えました。");
			return true;
		}
		return false;
	}

	/** 手札の数値合計値を算出するメソッド
	 * 
	 * @param list
	 * @return result
	 */
	public int listTotal(List<Integer> list) {
		int result = 0;
		for (int total : list) {
			total = cardNumberConverter(total);
			if (total > 10) {
				total = cardOverConverter(total);
			}
			result = result + total;
		}
		return result;
	}
}
