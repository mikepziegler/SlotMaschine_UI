package ch.mizye.SlotMaschine;

import java.util.*;

public class CCapital {

    private double Value;
    private double bet;

    public CCapital() {
        Value = 0;
        bet = 0;
    }

    public double getCapital() {
        return Value;
    }

    private void setCapital(double i) {
        Value = i;
    }

    public boolean checkCapital() {

        if (Value < 0.5 && getBet() == 0) {
            return false;
        }

        return true;
    }

    public void inputMoney(double v) {
        add(v);
    }

    private void add(double v) {
        Value +=  v;
    }

    private void remove(double v) {
        Value -= v;
    }

    public boolean setBet(double v) {

        if (v > Value) {
            return false;
        }

        remove(v);
        bet += v;

        return true;
    }

    public void removeBet() {
        bet = 0.0;
    }

    public double getBet() {
        return bet;
    }

    public boolean Betisset() {
        if (bet == 0) return false;

        return true;
    }

    public void payout() {
        setCapital(0.0);
    }

    public String checkSymbols(int w1, int w2, int w3) {
        String state = null;

        if (AND(w1, w2, w3)) {
            if (w1 == 8) {
                state = "all7";
                add(bet * 4);
            } else {
                state = "all";
                add(bet * 2);
            }
        } else if (Check2for7(w1, w2, w3)) {
            state = "double7";
            add(bet * 2);
        } else if (XOR(w1, w2, w3)) {
            state = "one7";
            add(bet);
        } else {
            state = "null";
        }

        setBet(0);

        return state;
    }

    private boolean XOR(int w1, int w2, int w3) {
        if (w1 == 8 || w2 == 8 || w3 == 8) {
            return true;
        }

        return false;
    }

    private boolean AND(int w1, int w2, int w3) {
        if (w1 == w2 && w2 == w3) {
            return true;
        }

        return false;
    }

    private boolean Check2for7(int w1, int w2, int w3) {
        if ((w1 == 8 && w2 == 8) || (w2 == 8 && w3 == 8) || (w1 == 8 && w3 == 8)) {
            return true;
        }

        return false;
    }
}
