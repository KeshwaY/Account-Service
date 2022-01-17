package account.accountant;

import org.springframework.stereotype.Component;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Component
public class PeriodTranslator {

    private final DateTimeFormatter dateTimeFormatter;

    public PeriodTranslator() {
        DateTimeFormatter dateTimeFormatterForPattern = DateTimeFormatter.ofPattern("MM-yyyy");
        this.dateTimeFormatter = new DateTimeFormatterBuilder().append(dateTimeFormatterForPattern)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
    }

    public LocalDateTime translatePeriodToLocalDateTime(String period) {
        return LocalDateTime.parse(period, dateTimeFormatter);
    }

    public String translateLocalDateTimeToPeriod(LocalDateTime dateTime) {
        String month = dateTime.getMonth().getValue() >= 10 ? String.valueOf(dateTime.getMonth().getValue()) : "0" + dateTime.getMonth().getValue();
        return month + "-" + dateTime.getYear();
    }

    public String translatePeriodNumericMonthToLiteral(String period) {
        String[] split = period.split("-");
        String monthName = new DateFormatSymbols().getMonths()[Integer.parseInt(split[0])-1];
        return monthName + "-" + split[1];
    }

}
