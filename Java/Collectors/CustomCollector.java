import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

class Student {
    int marks;

    Student() {
    }

    public Student(int i) {
        this.marks = i;
    }

    Student addMarks(Student s) {
        return new Student(this.marks = this.marks + s.marks);
    }

}

class StudentCollector implements Collector<Student, Student, Student> {
    @Override
    public Supplier<Student> supplier() {
        return Student::new;
    }

    @Override
    public BiConsumer<Student, Student> accumulator() {
        return Student::addMarks;
    }

    @Override
    public BinaryOperator<Student> combiner() {
        return Student::addMarks;
    }

    @Override
    public Function<Student, Student> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(IDENTITY_FINISH);
    }
}


class CustomCollector {
    public static void main(String[] args) {

        

    
}

    private BigDecimal calculateLifetimeEarnedAmount(CustomerAccountInfoEntity customerAccountInfo) {
    boolean cdlxEarnedAmountTimestampValid = isValidTimestamp(customerAccountInfo.getCdlxRedeemedAmountUpdateTs(), cdlxLifetimeAmountTimestampDifference);
    boolean figgEarnedAmountTimestampValid = isValidTimestamp(customerAccountInfo.getFiggRedeemedAmountUpdateTs(), figgLifetimeAmountTimestampDifference);

    if (!cdlxEarnedAmountTimestampValid || !figgEarnedAmountTimestampValid) {
        return null;
    }

    BigDecimal lifetimeEarnedAmount = calculateLifetimeAmount(customerAccountInfo);
    return lifetimeEarnedAmount;
}

private boolean isValidTimestamp(Timestamp timestamp, long maxTimestampDifference) {
    if (timestamp != null) {
        long timeDiff = Timestamp.from(Instant.now()).getTime() - timestamp.getTime();
        long timeDiffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
        log.info("Timestamp Difference: {}", timeDiffMinutes);
        return timeDiffMinutes <= maxTimestampDifference;
    }
    return false;
}

private BigDecimal calculateLifetimeAmount(CustomerAccountInfoEntity customerAccountInfo) {
    if (customerAccountInfo.getCdlxRedeemedAmountUpdateTs() == null && customerAccountInfo.getFiggRedeemedAmountUpdateTs() == null) {
        return BigDecimal.valueOf(0);
    }

    BigDecimal cdlxAmount = customerAccountInfo.getCdlxLifetimeReedemedAmount() != null ?
                            customerAccountInfo.getCdlxLifetimeReedemedAmount() : BigDecimal.valueOf(0);
    BigDecimal figgAmount = customerAccountInfo.getFiggLifetimeReedemedAmount() != null ?
                            customerAccountInfo.getFiggLifetimeReedemedAmount() : BigDecimal.valueOf(0);

    return cdlxAmount.add(figgAmount);
}

}
