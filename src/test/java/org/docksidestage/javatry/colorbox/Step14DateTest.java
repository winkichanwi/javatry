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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as plus-separated (e.g. 2019+04+24)? <br>
     * (カラーボックスに入っている日付をプラス記号区切り (e.g. 2019+04+24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        DateTimeFormatter plusDateFommatter = DateTimeFormatter.ofPattern("uuuu+MM+dd");
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> dateList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof LocalDate)
                .map(boxSpace -> (LocalDate) boxSpace.getContent())
                .map(localDate -> localDate.format(plusDateFommatter))
                .collect(Collectors.toList());
        List<String> localDateTimeList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof LocalDateTime)
                .map(boxSpace -> (LocalDateTime) boxSpace.getContent())
                .map(localDateTime -> localDateTime.format(plusDateFommatter))
                .collect(Collectors.toList());
        dateList.addAll(localDateTimeList);

        log(dateList.toString());
    }

    /**
     * How is it going to be if the slash-separated date string in yellow color-box is converted to LocaDate and toString() is used? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
        DateTimeFormatter slashDateFommatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> dateList = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Set<?>)
                .map(boxSpace -> (Set<?>) boxSpace.getContent())
                .flatMap(set -> Arrays.stream(set.toArray()))
                .filter(value -> value instanceof String)
                .map(value -> String.valueOf(value))
                .filter(text -> {
                    try {
                        LocalDate.parse(text, slashDateFommatter);
                        return true;
                    } catch (Exception ex) {
                        return false;
                    }
                })
                .map(text -> LocalDate.parse(text, slashDateFommatter))
                .map(localDate -> localDate.toString())
                .collect(Collectors.toList());

        log(dateList);

    }

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int monthSum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Temporal)
                .map(boxSpace -> (Temporal) boxSpace.getContent())
                .map(temporal -> LocalDate.from(temporal))
                .map(localDate -> localDate.getMonth().getValue())
                .reduce(0, (acc, value) -> acc + value);
        log(monthSum);
    }

    /**
     * Add 3 days to second-found date in color-boxes, what day of week is it? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Temporal> dateList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Temporal)
                .map(boxSpace -> (Temporal) boxSpace.getContent())
                .collect(Collectors.toList());
        if (dateList.size() >= 2) {
            LocalDate targetDate = LocalDate.from(dateList.get(1));
            String result = targetDate.plusDays(3).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPAN);
            log(result);
        } else {
            log("target not found");
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes? <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<LocalDate> dateList = colorBoxList.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContent() instanceof Temporal)
                .map(boxSpace -> (Temporal) boxSpace.getContent())
                .map(temporal -> LocalDate.from(temporal))
                .collect(Collectors.toList());
        if (dateList.size() >= 2) {
            log(Period.between(dateList.get(0), dateList.get(1)).getDays());
        } else {
            log("target not found");
        }
    }

    /**
     * Find LocalDate in yellow color-box,
     * and add same color-box's LocalDateTime's seconds as number of months to it,
     * and add red color-box's Long number as days to it,
     * and subtract the first decimal place of BigDecimal that has three(3) as integer in List in color-boxes from it,
     * What date is it? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
    }
}
