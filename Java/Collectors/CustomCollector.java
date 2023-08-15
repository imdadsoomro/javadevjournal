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

        List<CustomerOfferStatus> offerList = new ArrayList<>();
        offerList.add(new CustomerOfferStatus(new Date(), "NEW"));
        offerList.add(new CustomerOfferStatus(new Date(System.currentTimeMillis() - 3600000), "NEW"));
        offerList.add(new CustomerOfferStatus(new Date(), "SERVED"));
        offerList.add(new CustomerOfferStatus(new Date(System.currentTimeMillis() - 7200000), "SERVED"));
        offerList.add(new CustomerOfferStatus(new Date(), "ACTIVATED"));
        offerList.add(new CustomerOfferStatus(new Date(System.currentTimeMillis() - 1800000), "ACTIVATED"));

        Map<String, CustomerOfferStatus> filteredOfferMap = filterAndRetrieveMostRecent(offerList);
        List<CustomerOfferStatus> filteredOfferList = new ArrayList<>(filteredOfferMap.values());

        System.out.println(filteredOfferList);

        //-----
        Stream<Student> studentStream = Stream.of(new Student(30), new Student(40), new Student(50), new Student(80));
        System.out.println(studentStream.collect(new StudentCollector()).marks);

    
            List<CustomerOfferStatus> offerList = new ArrayList<>();

        // Add your CustomerOfferStatus objects to the offerList

        Map<String, CustomerOfferStatus> filteredOfferMap = filterAndRetrieveMostRecent(offerList);

        List<CustomerOfferStatus> filteredOfferList = new ArrayList<>(filteredOfferMap.values());
        System.out.println(filteredOfferList);
    }

        public static Map<String, CustomerOfferStatus> filterAndRetrieveMostRecent(List<CustomerOfferStatus> offerList) {
        return offerList.stream()
                .collect(Collectors.toMap(
                        CustomerOfferStatus::getOfferStatusName,
                        customerOfferStatus -> customerOfferStatus,
                        (existing, replacement) -> {
                            if (existing.getOfferStatusChangeTimeStamp().compareTo(replacement.getOfferStatusChangeTimeStamp()) > 0) {
                                return existing;
                            }
                            return replacement;
                        }
                ));
    }

    
}
}


...

    public static Map<String, CustomerOfferStatus> filterAndRetrieveMostRecent(List<CustomerOfferStatus> offerList) {
        return offerList.stream()
                .filter(offer -> offer.getOfferStatusChangeTimeStamp() != null) // Ignore objects with null offerStatusChangeTimeStamp
                .collect(Collectors.toMap(
                        CustomerOfferStatus::getOfferStatusName,
                        customerOfferStatus -> customerOfferStatus,
                        (existing, replacement) -> {
                            if (existing.getOfferStatusChangeTimeStamp().compareTo(replacement.getOfferStatusChangeTimeStamp()) > 0) {
                                return existing;
                            }
                            return replacement;
                        }
                ));
    }
