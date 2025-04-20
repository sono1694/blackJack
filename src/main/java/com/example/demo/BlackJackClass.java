package com.example.demo;

import java.util.Collections;
import java.util.Scanner;

import com.example.Bean.BlackJackBean;
import com.example.Logic.BlackJackLogic;

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
		//ボーンクラス呼び出し
		boonClass();
		
		System.out.println("ブラックジャックを終了します。");
	}

	public static void boonClass() {
		// インスタンス生成
		BlackJackBean bean = new BlackJackBean();
		BlackJackLogic logic = new BlackJackLogic();
		int i = 0;

		// プレイヤーの合計算出用変数を宣言
		int playerTotal = 0;
		// ディーラーの合計算出用変数を宣言
		int dealerTotal = 0;

		// 山札作成
		for (i = 0; i < bean.getList().size(); i++) {
			bean.getList().set(i, i);
		}

		// 山札をシャッフル
		Collections.shuffle(bean.getList());

		// プレイヤーとディーラーが1回目の山札から引くフェーズ
		bean.getPlayer().add(bean.getList().get(0));
		bean.getDealer().add(bean.getList().get(1));
		System.out.println("プレイヤーの手札：" + logic.cardConverter(bean.getPlayer().get(0)) + logic.converterAJQK(logic.cardNumberConverter(bean.getPlayer().get(0))));
		System.out.println("ディーラーの手札：" + logic.cardConverter(bean.getDealer().get(0)) + logic.converterAJQK(logic.cardNumberConverter(bean.getDealer().get(0))));

		// 山札の引いた回数保有
		int draw = 2;

		// プレイヤーのドローフェーズ確認
		for (i = 0; i < bean.getList().size(); i++) {
			System.out.println("カードをドローしますか？（「y」or「n」を入力してください）");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			String str = scan.next();

			if (str.equals("y")) {
				// プレイヤーのリストに山札から1枚追加
				bean.getPlayer().add(bean.getList().get(i + draw));
				draw = draw + i;
				System.out.println("プレイヤーの手札：" + logic.cardConverter(bean.getPlayer().get(i + 1)) 
						+ logic.converterAJQK(logic.cardNumberConverter(bean.getPlayer().get(i + 1))));
				// プレイヤーの手札合計算出
				playerTotal = logic.listTotal(bean.getPlayer());
				System.out.println("プレイヤーの手札合計値：" + playerTotal);

				// バースト確認
				Boolean result = logic.cardBurst(playerTotal);
				if (result) {
					break;
				}

			} else if (str.equals("n")) {
				// ループから抜ける。
				break;
			} else {
				System.out.println("想定外の入力がされました。もう一度入力してください");
				i--;
			}
		}

		for (i = 0; i < bean.getList().size(); i++) {
			int totalResult = 0;
			for (int total : bean.getDealer()) {
				totalResult = totalResult + total;
			}
			if (totalResult >= 17) {
				// ディーラーのリストに山札から1枚追加
				bean.getDealer().add(logic.cardNumberConverter(bean.getList().get(i + draw)));
				draw = draw + i;
				
				// ディーラーの手札合計算出
				dealerTotal = logic.listTotal(bean.getDealer());

				// デバック用
				System.out.println("ディーラーの手札合計値：" + dealerTotal);

				// バースト確認
				Boolean result = logic.cardBurst(dealerTotal);
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
		} else {
			System.out.println("引き分けです。");
		}
	}



}
