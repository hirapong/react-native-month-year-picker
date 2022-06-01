package com.gusparis.monthpicker.builder;
import android.widget.NumberPicker;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class YearNumberPicker extends MonthYearNumberPicker {

  private static final int DEFAULT_SIZE = 204;

  @Override
  YearNumberPicker onScrollListener(MonthYearScrollListener scrollListener) {
    yearPicker.setOnScrollListener(scrollListener);
    yearPicker.setOnValueChangedListener(scrollListener);
    return this;
  }

  @Override
  YearNumberPicker build() {
    int year = props.value().getYear();
    yearPicker.setMinValue(year - DEFAULT_SIZE);
    yearPicker.setMaxValue(year + DEFAULT_SIZE);
    yearPicker.setFormatter(new NumberPicker.Formatter() {
      @Override
      public String format(int value) {
        return value + "年";
      }
    });
    yearPicker.setValue(year);
    // Fix for Formatter blank initial rendering
    try {
      final Method method = yearPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
      method.setAccessible(true);
      method.invoke(yearPicker, true);

    } catch (final NoSuchMethodException | InvocationTargetException |
        IllegalAccessException | IllegalArgumentException e) {
      e.printStackTrace();
    }
    return this;
  }

  @Override
  synchronized void setValue() {
    int year = yearPicker.getValue();
    int value = year;
    if (props.minimumValue() != null && year < props.minimumValue().getYear()) {
      value = props.minimumValue().getYear();
    } else if (props.maximumValue() != null && year > props.maximumValue().getYear()) {
      value = props.maximumValue().getYear();
    }
    yearPicker.setValue(value);
  }

  @Override
  int getValue() {
    return yearPicker.getValue();
  }
}
