I have following java code that generates snowflake query: StringBuilder sqlQuery = new StringBuilder();
List<String> searchTermsList; List<String> conditions = searchTermsList
        .stream().map(value-> " midList LIKE '%" + value.replace("'", "\\'") + "%'").collect(Collectors.toList());
sqlQuery = “Select * from tableName where colVal ”
.append(String.join(" OR ", conditions));

Select * from tableName where colVal LIKE searchTermsList.get(0) OR  colVal LIKE searchTermsList.get(1) OR  colVal LIKE searchTermsList.get(2))

Please rewrite the java code so that I don’t have to write LIKE multiple times. searchTermsList can have n number of String elements.

        select * from MASTER_MERCHANT_MID where MERCHANT_ID LIKE '%3291188%' OR  MERCHANT_ID LIKE '%73413885%' order by MERCHANT_ID;
select * from MASTER_MERCHANT_MID where MERCHANT_ID ILIKE ANY ('%3291188%','%73413885%') order by MERCHANT_ID 
