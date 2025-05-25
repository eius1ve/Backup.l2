/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.templates.PlayerTemplate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package model;

import GabrielBoards.ClassPathsHelper;
import l2.gameserver.model.Player;
import l2.gameserver.templates.PlayerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerGab
extends Player {
    private int _middleOff = 0;
    private int _leftOff = 0;
    private int _leftOff1 = 0;
    private int _leftOff1_1 = 0;
    private int _leftOff1_2 = 0;
    private int _leftOff2 = 0;
    private int _leftOff2_1 = 0;
    private int _leftOff2_2 = 0;
    private int _rightOff = 0;
    private int _rightOff1 = 0;
    private int _rightOff1_1 = 0;
    private int _rightOff1_2 = 0;
    private int _rightOff2 = 0;
    private int _rightOff2_1 = 0;
    private int _rightOff2_2 = 0;
    private int _middleMage = 0;
    private int _leftMage = 0;
    private int _leftMage1 = 0;
    private int _leftMage1_1 = 0;
    private int _leftMage1_2 = 0;
    private int _leftMage2 = 0;
    private int _leftMage2_1 = 0;
    private int _leftMage2_2 = 0;
    private int _rightMage = 0;
    private int _rightMage1 = 0;
    private int _rightMage1_1 = 0;
    private int _rightMage1_2 = 0;
    private int _rightMage2 = 0;
    private int _rightMage2_1 = 0;
    private int _rightMage2_2 = 0;
    private int _middleDef = 0;
    private int _leftDef = 0;
    private int _leftDef1 = 0;
    private int _leftDef1_1 = 0;
    private int _leftDef1_2 = 0;
    private int _leftDef2 = 0;
    private int _leftDef2_1 = 0;
    private int _leftDef2_2 = 0;
    private int _rightDef = 0;
    private int _rightDef1 = 0;
    private int _rightDef1_1 = 0;
    private int _rightDef1_2 = 0;
    private int _rightDef2 = 0;
    private int _rightDef2_1 = 0;
    private int _rightDef2_2 = 0;
    private int _middleSup = 0;
    private int _leftSup = 0;
    private int _leftSup1 = 0;
    private int _leftSup1_1 = 0;
    private int _leftSup1_2 = 0;
    private int _leftSup2 = 0;
    private int _leftSup2_1 = 0;
    private int _leftSup2_2 = 0;
    private int _rightSup = 0;
    private int _rightSup1 = 0;
    private int _rightSup1_1 = 0;
    private int _rightSup1_2 = 0;
    private int _rightSup2 = 0;
    private int _rightSup2_1 = 0;
    private int _rightSup2_2 = 0;
    private int _alekoscur = 0;
    private int _alekos = 0;
    private static final Logger F = LoggerFactory.getLogger(PlayerGab.class);

    public PlayerGab(int arg0, PlayerTemplate arg1, String arg2) {
        super(arg0, arg1, arg2);
    }

    public void incMiddleOff() {
        ++this._middleOff;
    }

    public void setMiddleOff(int middle) {
        this._middleOff = middle;
    }

    public int getMiddleOff() {
        return this._middleOff;
    }

    public void incLeftOff() {
        ++this._leftOff;
    }

    public void setLeftOff(int left) {
        this._leftOff = left;
    }

    public int getLeftOff() {
        return this._leftOff;
    }

    public void incLeftOff1() {
        ++this._leftOff1;
    }

    public void setLeftOff1(int left) {
        this._leftOff1 = left;
    }

    public int getLeftOff1() {
        return this._leftOff1;
    }

    public void incLeftOff1_1() {
        ++this._leftOff1_1;
    }

    public void setLeftOff1_1(int left) {
        this._leftOff1_1 = left;
    }

    public int getLeftOff1_1() {
        return this._leftOff1_1;
    }

    public void incLeftOff1_2() {
        ++this._leftOff1_2;
    }

    public void setLeftOff1_2(int left) {
        this._leftOff1_2 = left;
    }

    public int getLeftOff1_2() {
        return this._leftOff1_2;
    }

    public void incLeftOff2() {
        ++this._leftOff2;
    }

    public void setLeftOff2(int left) {
        this._leftOff2 = left;
    }

    public int getLeftOff2() {
        return this._leftOff2;
    }

    public void incLeftOff2_1() {
        ++this._leftOff2_1;
    }

    public void setLeftOff2_1(int left) {
        this._leftOff2_1 = left;
    }

    public int getLeftOff2_1() {
        return this._leftOff2_1;
    }

    public void incLeftOff2_2() {
        ++this._leftOff2_2;
    }

    public void setLeftOff2_2(int left) {
        this._leftOff2_2 = left;
    }

    public int getLeftOff2_2() {
        return this._leftOff2_2;
    }

    public void incRightOff() {
        ++this._rightOff;
    }

    public void setRightOff(int right) {
        this._rightOff = right;
    }

    public int getRightOff() {
        return this._rightOff;
    }

    public void incRightOff1() {
        ++this._rightOff1;
    }

    public void setRightOff1(int right) {
        this._rightOff1 = right;
    }

    public int getRightOff1() {
        return this._rightOff1;
    }

    public void incRightOff1_1() {
        ++this._rightOff1_1;
    }

    public void setRightOff1_1(int right) {
        this._rightOff1_1 = right;
    }

    public int getRightOff1_1() {
        return this._rightOff1_1;
    }

    public void incRightOff1_2() {
        ++this._rightOff1_2;
    }

    public void setRightOff1_2(int right) {
        this._rightOff1_2 = right;
    }

    public int getRightOff1_2() {
        return this._rightOff1_2;
    }

    public void incRightOff2() {
        ++this._rightOff2;
    }

    public void setRightOff2(int right) {
        this._rightOff2 = right;
    }

    public int getRightOff2() {
        return this._rightOff2;
    }

    public void incRightOff2_1() {
        ++this._rightOff2_1;
    }

    public void setRightOff2_1(int right) {
        this._rightOff2_1 = right;
    }

    public int getRightOff2_1() {
        return this._rightOff2_1;
    }

    public void incRightOff2_2() {
        ++this._rightOff2_2;
    }

    public void setRightOff2_2(int right) {
        this._rightOff2_2 = right;
    }

    public int getRightOff2_2() {
        return this._rightOff2_2;
    }

    public void incMiddleMage() {
        ++this._middleMage;
    }

    public void setMiddleMage(int middle) {
        this._middleMage = middle;
    }

    public int getMiddleMage() {
        return this._middleMage;
    }

    public void incLeftMage() {
        ++this._leftMage;
    }

    public void setLeftMage(int left) {
        this._leftMage = left;
    }

    public int getLeftMage() {
        return this._leftMage;
    }

    public void incLeftMage1() {
        ++this._leftMage1;
    }

    public void setLeftMage1(int left) {
        this._leftMage1 = left;
    }

    public int getLeftMage1() {
        return this._leftMage1;
    }

    public void incLeftMage1_1() {
        ++this._leftMage1_1;
    }

    public void setLeftMage1_1(int left) {
        this._leftMage1_1 = left;
    }

    public int getLeftMage1_1() {
        return this._leftMage1_1;
    }

    public void incLeftMage1_2() {
        ++this._leftMage1_2;
    }

    public void setLeftMage1_2(int left) {
        this._leftMage1_2 = left;
    }

    public int getLeftMage1_2() {
        return this._leftMage1_2;
    }

    public void incLeftMage2() {
        ++this._leftMage2;
    }

    public void setLeftMage2(int left) {
        this._leftMage2 = left;
    }

    public int getLeftMage2() {
        return this._leftMage2;
    }

    public void incLeftMage2_1() {
        ++this._leftMage2_1;
    }

    public void setLeftMage2_1(int left) {
        this._leftMage2_1 = left;
    }

    public int getLeftMage2_1() {
        return this._leftMage2_1;
    }

    public void incLeftMage2_2() {
        ++this._leftMage2_2;
    }

    public void setLeftMage2_2(int left) {
        this._leftMage2_2 = left;
    }

    public int getLeftMage2_2() {
        return this._leftMage2_2;
    }

    public void incRightMage() {
        ++this._rightMage;
    }

    public void setRightMage(int right) {
        this._rightMage = right;
    }

    public int getRightMage() {
        return this._rightMage;
    }

    public void incRightMage1() {
        ++this._rightMage1;
    }

    public void setRightMage1(int right) {
        this._rightMage1 = right;
    }

    public int getRightMage1() {
        return this._rightMage1;
    }

    public void incRightMage1_1() {
        ++this._rightMage1_1;
    }

    public void setRightMage1_1(int right) {
        this._rightMage1_1 = right;
    }

    public int getRightMage1_1() {
        return this._rightMage1_1;
    }

    public void incRightMage1_2() {
        ++this._rightMage1_2;
    }

    public void setRightMage1_2(int right) {
        this._rightMage1_2 = right;
    }

    public int getRightMage1_2() {
        return this._rightMage1_2;
    }

    public void incRightMage2() {
        ++this._rightMage2;
    }

    public void setRightMage2(int right) {
        this._rightMage2 = right;
    }

    public int getRightMage2() {
        return this._rightMage2;
    }

    public void incRightMage2_1() {
        ++this._rightMage2_1;
    }

    public void setRightMage2_1(int right) {
        this._rightMage2_1 = right;
    }

    public int getRightMage2_1() {
        return this._rightMage2_1;
    }

    public void incRightMage2_2() {
        ++this._rightMage2_2;
    }

    public void setRightMage2_2(int right) {
        this._rightMage2_2 = right;
    }

    public int getRightMage2_2() {
        return this._rightMage2_2;
    }

    public void incMiddleDef() {
        ++this._middleDef;
    }

    public void setMiddleDef(int middle) {
        this._middleDef = middle;
    }

    public int getMiddleDef() {
        return this._middleDef;
    }

    public void incLeftDef() {
        ++this._leftDef;
    }

    public void setLeftDef(int left) {
        this._leftDef = left;
    }

    public int getLeftDef() {
        return this._leftDef;
    }

    public void incLeftDef1() {
        ++this._leftDef1;
    }

    public void setLeftDef1(int left) {
        this._leftDef1 = left;
    }

    public int getLeftDef1() {
        return this._leftDef1;
    }

    public void incLeftDef1_1() {
        ++this._leftDef1_1;
    }

    public void setLeftDef1_1(int left) {
        this._leftDef1_1 = left;
    }

    public int getLeftDef1_1() {
        return this._leftDef1_1;
    }

    public void incLeftDef1_2() {
        ++this._leftDef1_2;
    }

    public void setLeftDef1_2(int left) {
        this._leftDef1_2 = left;
    }

    public int getLeftDef1_2() {
        return this._leftDef1_2;
    }

    public void incLeftDef2() {
        ++this._leftDef2;
    }

    public void setLeftDef2(int left) {
        this._leftDef2 = left;
    }

    public int getLeftDef2() {
        return this._leftDef2;
    }

    public void incLeftDef2_1() {
        ++this._leftDef2_1;
    }

    public void setLeftDef2_1(int left) {
        this._leftDef2_1 = left;
    }

    public int getLeftDef2_1() {
        return this._leftDef2_1;
    }

    public void incLeftDef2_2() {
        ++this._leftDef2_2;
    }

    public void setLeftDef2_2(int left) {
        this._leftDef2_2 = left;
    }

    public int getLeftDef2_2() {
        return this._leftDef2_2;
    }

    public void incRightDef() {
        ++this._rightDef;
    }

    public void setRightDef(int right) {
        this._rightDef = right;
    }

    public int getRightDef() {
        return this._rightDef;
    }

    public void incRightDef1() {
        ++this._rightDef1;
    }

    public void setRightDef1(int right) {
        this._rightDef1 = right;
    }

    public int getRightDef1() {
        return this._rightDef1;
    }

    public void incRightDef1_1() {
        ++this._rightDef1_1;
    }

    public void setRightDef1_1(int right) {
        this._rightDef1_1 = right;
    }

    public int getRightDef1_1() {
        return this._rightDef1_1;
    }

    public void incRightDef1_2() {
        ++this._rightDef1_2;
    }

    public void setRightDef1_2(int right) {
        this._rightDef1_2 = right;
    }

    public int getRightDef1_2() {
        return this._rightDef1_2;
    }

    public void incRightDef2() {
        ++this._rightDef2;
    }

    public void setRightDef2(int right) {
        this._rightDef2 = right;
    }

    public int getRightDef2() {
        return this._rightDef2;
    }

    public void incRightDef2_1() {
        ++this._rightDef2_1;
    }

    public void setRightDef2_1(int right) {
        this._rightDef2_1 = right;
    }

    public int getRightDef2_1() {
        return this._rightDef2_1;
    }

    public void incRightDef2_2() {
        ++this._rightDef2_2;
    }

    public void setRightDef2_2(int right) {
        this._rightDef2_2 = right;
    }

    public int getRightDef2_2() {
        return this._rightDef2_2;
    }

    public void incMiddleSup() {
        ++this._middleSup;
    }

    public void setMiddleSup(int middle) {
        this._middleSup = middle;
    }

    public int getMiddleSup() {
        return this._middleSup;
    }

    public void incLeftSup() {
        ++this._leftSup;
    }

    public void setLeftSup(int left) {
        this._leftSup = left;
    }

    public int getLeftSup() {
        return this._leftSup;
    }

    public void incLeftSup1() {
        ++this._leftSup1;
    }

    public void setLeftSup1(int left) {
        this._leftSup1 = left;
    }

    public int getLeftSup1() {
        return this._leftSup1;
    }

    public void incLeftSup1_1() {
        ++this._leftSup1_1;
    }

    public void setLeftSup1_1(int left) {
        this._leftSup1_1 = left;
    }

    public int getLeftSup1_1() {
        return this._leftSup1_1;
    }

    public void incLeftSup1_2() {
        ++this._leftSup1_2;
    }

    public void setLeftSup1_2(int left) {
        this._leftSup1_2 = left;
    }

    public int getLeftSup1_2() {
        return this._leftSup1_2;
    }

    public void incLeftSup2() {
        ++this._leftSup2;
    }

    public void setLeftSup2(int left) {
        this._leftSup2 = left;
    }

    public int getLeftSup2() {
        return this._leftSup2;
    }

    public void incLeftSup2_1() {
        ++this._leftSup2_1;
    }

    public void setLeftSup2_1(int left) {
        this._leftSup2_1 = left;
    }

    public int getLeftSup2_1() {
        return this._leftSup2_1;
    }

    public void incLeftSup2_2() {
        ++this._leftSup2_2;
    }

    public void setLeftSup2_2(int left) {
        this._leftSup2_2 = left;
    }

    public int getLeftSup2_2() {
        return this._leftSup2_2;
    }

    public void incRightSup() {
        ++this._rightSup;
    }

    public void setRightSup(int right) {
        this._rightSup = right;
    }

    public int getRightSup() {
        return this._rightSup;
    }

    public void incRightSup1() {
        ++this._rightSup1;
    }

    public void setRightSup1(int right) {
        this._rightSup1 = right;
    }

    public int getRightSup1() {
        return this._rightSup1;
    }

    public void incRightSup1_1() {
        ++this._rightSup1_1;
    }

    public void setRightSup1_1(int right) {
        this._rightSup1_1 = right;
    }

    public int getRightSup1_1() {
        return this._rightSup1_1;
    }

    public void incRightSup1_2() {
        ++this._rightSup1_2;
    }

    public void setRightSup1_2(int right) {
        this._rightSup1_2 = right;
    }

    public int getRightSup1_2() {
        return this._rightSup1_2;
    }

    public void incRightSup2() {
        ++this._rightSup2;
    }

    public void setRightSup2(int right) {
        this._rightSup2 = right;
    }

    public int getRightSup2() {
        return this._rightSup2;
    }

    public void incRightSup2_1() {
        ++this._rightSup2_1;
    }

    public void setRightSup2_1(int right) {
        this._rightSup2_1 = right;
    }

    public int getRightSup2_1() {
        return this._rightSup2_1;
    }

    public void incRightSup2_2() {
        ++this._rightSup2_2;
    }

    public void setRightSup2_2(int right) {
        this._rightSup2_2 = right;
    }

    public int getRightSup2_2() {
        return this._rightSup2_2;
    }

    public void decalekoscur() {
        --this._alekoscur;
    }

    public void incalekoscur() {
        ++this._alekoscur;
    }

    public void setalekoscur(int alekos) {
        this._alekoscur = alekos;
    }

    public int getalekoscur() {
        return this._alekoscur;
    }

    public void setalekos(int alekos) {
        this._alekos = alekos;
    }

    public void resetAlekos() {
        int points = ClassPathsHelper.getClassPathPoints(this);
        this.setalekos(points);
    }

    public int getalekos() {
        this.resetAlekos();
        return this._alekos;
    }
}
