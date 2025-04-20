package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackJackClass {

//	カード枚数は52枚。ジョーカーは含めない。カードの重複が無いように山札を構築する。
//	プレイヤー、ディーラーの一対一で対戦するものとし、以下の挙動を取る
//	初期設定として、プレイヤー・ディーラーが交互に1枚ずつ山札からカードを取り手札とする。
//	プレイヤーからは自分の手札すべてと、ディーラーの1枚めの手札が確認できる。（ディーラーの2枚目移行の手札はわからない）
//
//	手札はAが1ポイント、2-10がそれぞれ2-10ポイント、J/Q/Kが10ポイントとして計算される。
//
//	プレイヤーは手札を1枚追加するか、しないかを選択できる。
//	手札を追加した場合、21ポイントを超えるとバーストとなり、ゲームに敗北する。
//	プレイヤーはバーストするか、好きなタイミングで止めるまで手札にカードを追加できる。
//	ディーラーは手札の合計ポイントが17以上になるまで山札を引き続ける。
//	ディーラーの手札が21ポイントを超えた場合、バーストしてプレイヤーの勝利。
//	ディーラーの手札が18以上21以下になったとき次の段階に移行する。
//
//	プレイヤー・ディーラーの手札のポイントを比較して、大きいほうが勝利。
//
//	ダブルダウンやスプリットなどの特殊ルールは無し。

	public static void main(String[] args) {

		System.out.println("ブラックジャックを開始します。");
		boonClass();
	}

	public static void boonClass() {

		ArrayList<Integer> list = new ArrayList<>(Collections.nCopies(52, 0));
		List<Integer> player = new ArrayList<>();
		List<Integer> dealer = new ArrayList<>();

		int i = 0;

		// 
		int playerTotal = 0;
		int dealerTotal = 0;

		// 山札作成
		for (i = 0; i < list.size(); i++) {
			list.set(i, i);
		}

		// 山札をシャッフル
		Collections.shuffle(list);

		// プレイヤーとディーラーが1回目の山札から引くフェーズ
		player.add(list.get(0));
		dealer.add(list.get(1));
		System.out.println("プレイヤーの手札：" + cardConverter(player.get(0)) + converterAJQK(cardNumberConverter(player.get(0))));
		System.out.println("ディーラーの手札：" + cardConverter(dealer.get(0)) + converterAJQK(cardNumberConverter(dealer.get(0))));

		// 山札の引いた回数保有
		int draw = 2;

		// プレイヤーのドローフェーズ確認
		for (i = 0; i < list.size(); i++) {
			System.out.println("カードをドローしますか？（yかnを入力してください）");
			Scanner scan = new Scanner(System.in);
			String str = scan.next();

			if (str.equals("y")) {
				// プレイヤーのリストに山札から1枚追加
				player.add(list.get(i + draw));
				draw = draw + i;
				System.out.println("プレイヤーの手札：" + cardConverter(player.get(i + 1)) 
						+ converterAJQK(cardNumberConverter(player.get(i + 1))));
				// プレイヤーの手札合計算出
				playerTotal = listTotal(player);
				System.out.println("プレイヤーの手札合計値：" + playerTotal);

				// バースト確認
				Boolean result = cardBurst(playerTotal);
				if (result) {
					System.exit(0);
				}

			} else if (str.equals("n")) {
				// ループから抜ける。
				break;
			} else {
				System.out.println("想定外の入力がされました。もう一度入力してください");
				i--;
			}
		}

		for (i = 0; i < list.size(); i++) {
			int totalResult = 0;
			for (int total : dealer) {
				totalResult = totalResult + total;
			}
			if (totalResult > 17) {
				// ディーラーのリストに山札から1枚追加
				dealer.add(cardNumberConverter(list.get(i + draw)));
				draw = draw + i;
				
				// ディーラーの手札合計算出
				dealerTotal = listTotal(dealer);

				// デバック用
				System.out.println("ディーラーの手札合計値：" + dealerTotal);

				// バースト確認
				Boolean result = cardBurst(dealerTotal);
				if (result) {
					System.out.println("ディーラーがオーバーしました。");
					System.out.println("プレイヤーの勝利です。");
					System.exit(0);
				}

			} else {
				System.out.println("ディーラーがオーバーしました。");
				break;
			}
		}

		if (playerTotal > dealerTotal) {
			System.out.println("プレイヤーの勝利です。");
		} else if (playerTotal < dealerTotal) {
			System.out.println("ディーラーの勝利です。");
		} else
			System.out.println("引き分けです。");
	}

	// カードの種類変換
	public static String cardConverter(int cardNumber) {
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

	// カードナンバーを1～13に変換
	public static int cardNumberConverter(int cardNumber) {
		int pos = cardNumber % 13;
		if (pos == 0) {
			return 13;
		} else {
			return pos;
		}
	}

	// カードの1/11/12/13をA/J/Q/Kに変換
	public static String converterAJQK(int cardNumber) {
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

	// カードがJ/Q/Kの場合、10に変換
	public static int cardOverConverter(int cardNumber) {
		if (cardNumber > 10) {
			return cardNumber = 10;
		}
		return cardNumber;
	}

	// 手札がバーストしたか確認
	public static Boolean cardBurst(int result) {
		if (result > 21) {
			System.out.println("手札が21を超えました。");
			return true;
		}
		return false;
	}

	public static int listTotal(List<Integer> list) {
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
