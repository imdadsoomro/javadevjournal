import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsExamples {

    public static void main(String[] args){
        /*
        I have following two hashmaps in java. I want to merge these maps into one hashmap. If the key is same, i want to compare the fields in value object. If page, section and fieldId are different in CHCompanyDefaultsModel, i want to keep the values from chDefaultsResponseMap. If they are same, i want to keep one record. If only one is available then i want to keep that one value object. Can you write a code with the help of java8.  
        chDefaultsResponseMap = {HashMap@28655}  size = 1
 {Long@28665} 17062 -> {ArrayList@28666}  size = 83
  key = {Long@28665} 17062
   value = 17062
  value = {ArrayList@28666}  size = 83
   0 = {CHCompanyDefaultsModel@28668} "CHCompanyDefaultsModel{name='dbaName', section='business_information', page='API', defaultValue='', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=124, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   1 = {CHCompanyDefaultsModel@28669} "CHCompanyDefaultsModel{name='legal_business_name', section='business_information', page='API', defaultValue='MMIS TEST LEGAL', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=93, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   2 = {CHCompanyDefaultsModel@28670} "CHCompanyDefaultsModel{name='businessStartedOn', section='business_information', page='API', defaultValue='2023-01-01', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=167, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   3 = {CHCompanyDefaultsModel@28671} "CHCompanyDefaultsModel{name='organizationType', section='business_information', page='API', defaultValue='Sole Proprietorship', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=40, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"



   chDefaultsAdminResponseMap = {HashMap@28659}  size = 1
 {Long@29054} 17062 -> {ArrayList@29055}  size = 146
  key = {Long@29054} 17062
  value = {ArrayList@29055}  size = 146
   0 = {CHCompanyDefaultsModel@29057} "CHCompanyDefaultsModel{name='dbaName', section='business_information', page='API', defaultValue='', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=124, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   1 = {CHCompanyDefaultsModel@29058} "CHCompanyDefaultsModel{name='legal_business_name', section='business_information', page='API', defaultValue='MMIS TEST LEGAL', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=93, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   2 = {CHCompanyDefaultsModel@29059} "CHCompanyDefaultsModel{name='businessStartedOn', section='business_information', page='API', defaultValue='2023-01-01', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=167, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
   3 = {CHCompanyDefaultsModel@29060} "CHCompanyDefaultsModel{name='organizationType', section='business_information', page='API', defaultValue='Sole Proprietorship', type='TEXTBOX', editable=true, visible=false, pii=false, options=null, fieldId=40, companyId=17062, prefix='null', postfix='null', menuItemKey='null', regex='null', required=true}"
        */
        System.out.println("*****Examples of different Collectors methods*****");

        System.out.println("Example of Collectors.toList : ");
        System.out.println(Stream.of(4,3,2,7,1).collect(Collectors.toList()));

        System.out.println("***********************");
        System.out.println("Example of Collectors.toSet : ");
        System.out.println(Stream.of(1,7,9,2,3,1).collect(Collectors.toSet()));

        System.out.println("***********************");
        System.out.println("Example of Collectors.toCollection : ");
        Stream<Integer> stream = Stream.of(1,7,9,2,3,1);

        Set<Integer> set = stream.collect(Collectors
                .toCollection(TreeSet::new));
        System.out.println(set);

        System.out.println("***********************");
        System.out.println("Example of toMap and toConcurrentMap : ");
        Stream<String> wordStream = Stream.of("one", "hello", "opera", "hi",
                "music");
        HashMap<Character, String> m = wordStream.collect(
                Collectors.toMap(word -> word.charAt(0),
                        word -> word,
                        (word1, word2) -> word1 +","+ word2,
                        HashMap::new));
        System.out.println(m);

        System.out.println("***********************");
        System.out.println("Example of summingInt,summingLong and summingDouble : ");
        Stream<Integer> intStream = Stream.of(1,2,3,4,5);
        System.out.println(intStream.collect(Collectors.summingInt(Integer::intValue)));

        System.out.println("***********************");
        System.out.println("Example of averagingInt,averagingLong and averagingDouble : ");
        Stream<Integer> intStream1 = Stream.of(5,5,4,6,4);
        System.out.println(intStream1.collect(Collectors.averagingInt(Integer::intValue)));

        System.out.println("***********************");
        System.out.println("Example of counting, maxBy, minBy : ");
        Stream<String> strStream = Stream.of("1","2","3","4","5");
        System.out.println(strStream.collect(Collectors.counting()));

        Stream<Integer> intStream2 = Stream.of(4,2,6,12,33,44);
        System.out.println(intStream2.collect(Collectors.maxBy(Comparator.naturalOrder())));

        System.out.println("***********************");
        System.out.println("Example of groupingBy and partitioningBy : ");
        Stream<String> strStream2 = Stream.of("one","two","b","d","three");
        System.out.println(strStream2.collect(Collectors.groupingBy(String::length)));

        Stream<Integer> intStream3 = Stream.of(1, 4, 5, 2, 7);
        System.out.println(intStream3.collect(Collectors.partitioningBy(e -> e % 2 == 0)));

        System.out.println("***********************");
        System.out.println("Example of summarizingInt,summarizingDouble and summarizingLong : ");
        Stream<Integer> intStream4 = Stream.of(5, 4, 3, 2, 1);
        Map<Integer, IntSummaryStatistics> map = intStream4.collect(Collectors.groupingBy(Integer::intValue, Collectors.summarizingInt(Integer::intValue)));

        System.out.println(map);

        System.out.println("***********************");
        System.out.println("Example of joining : ");
        Stream<String> strStream3 = Stream.of("one","two","three","four","five");
        System.out.println(strStream3.collect(Collectors.joining()));

        Stream<String> strStream4 = Stream.of("one","two","three","four","five");
        System.out.println(strStream4.collect(Collectors.joining("-")));

        Stream<String> strStream5 = Stream.of("one","two","three","four","five");
        System.out.println(strStream5.collect(Collectors.joining("-","start:",":end")));

        System.out.println("***********************");
        System.out.println("Example of mapping,flatMapping and reducing : ");
        Stream<String> strStream6 = Stream.of("one","two","three","four");
        System.out.println(strStream6.collect(Collectors.groupingBy(String::length,Collectors.mapping(String::toUpperCase,Collectors.toSet()))));

        Stream<String> strStream7 = Stream.of("one","two","three","four");
        System.out.println(strStream7.collect(Collectors.reducing((str1,str2) -> str1 + str2)).orElse("-"));

        System.out.println("***********************");
        System.out.println("Example of collectingAndThen : ");
        Stream<String> strStream8 = Stream.of("sunday","monday","tuesday","wednesday");
        List<String> strList = strStream8.collect(Collectors.collectingAndThen(Collectors.toList(),Collections::unmodifiableList));
        System.out.println(strList);
    }
}
