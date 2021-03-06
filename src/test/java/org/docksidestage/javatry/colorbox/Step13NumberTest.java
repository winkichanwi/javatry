/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54 (includes)? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long count = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Integer)
                .map(content -> (Integer) content)
                .filter(integer -> integer >= 0 && integer <= 54)
                .count();
        log("No of integer-type values in color-boxes between 0 and 54: " + count);
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long countNumer = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Number)
                .map(content -> (Number) content)
                .filter(number -> number.doubleValue() >= 0 && number.doubleValue() <= 54)
                .count();
        log("No of integer-type values in color-boxes between 0 and 54: " + countNumer);
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> colorOpt = colorBoxList.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream().anyMatch(space -> space.getContent() instanceof Integer))
                .max(Comparator.comparingInt(colorBox -> colorBox.getSize().getWidth()))
                .map(colorBox -> colorBox.getColor().getColorName());
        log(colorOpt.orElse("No target colorbox found"));
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        BigDecimal sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof List)
                .map(boxSpace -> (List<?>) boxSpace.getContent())
                .flatMap(List::stream)
                .filter(content -> content instanceof BigDecimal)
                .map(content -> (BigDecimal) content)
                .reduce(BigDecimal.ZERO, (BigDecimal acc, BigDecimal bd) -> {
                    return acc.add(bd);
                });

        log(sum);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Object> maxKey = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Map<?, ?>)
                .map(boxSpace -> (Map<?, ?>) boxSpace.getContent())
                .flatMap(content -> content.entrySet().stream())
                .filter(set -> set.getValue() instanceof Number)
                .max(Comparator.comparingDouble(set -> ((Number) set.getValue()).doubleValue()))
                .map(set -> set.getKey());
        log(maxKey.orElse("No target object found"));
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br>
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        double sumNumber = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("purple"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Map<?, ?>)
                .map(boxSpace -> (Map<?, ?>) boxSpace.getContent())
                .flatMap(content -> content.entrySet().stream())
                .filter(set -> set.getValue() instanceof Number)
                .map(set -> (Number) set.getValue())
                .mapToDouble(number -> number.doubleValue())
                .sum();
        double sumString = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("purple"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Map<?, ?>)
                .map(boxSpace -> (Map<?, ?>) boxSpace.getContent())
                .flatMap(content -> content.entrySet().stream())
                .filter(set -> set.getValue() instanceof String)
                .map(set -> (String) set.getValue())
                .mapToDouble(string -> {
                    try {
                        return Double.parseDouble(string);
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .sum();
        log(sumNumber + sumString);
    }
}
