## Inventory Service

This is a simple inventory service that allows you to create, read, update, and delete items in an inventory.

<table>
    <thead>
        <tr>
            <th></th>
            <th>Cars</th>
            <th>Brands</th>
            <th>Models</th>
        </tr>
    </thead>
    <tbody>
<code>http://[host]:[port]/inventory-service/api/v1</code>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/cars</td>
            <td>/brands</td>
            <td>/models</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/cars/1</td>
            <td>/brands/1</td>
            <td>/models/1</td>
        </tr>
        <tr>
            <td>$\textcolor{orange}{POST}$</td>
            <td>/cars</td>
            <td>/brands</td>
            <td>/models</td>
        </tr>
        <tr>
            <td>$\textcolor{violet}{PUT}$</td>
            <td>/cars/1</td>
            <td>/brands/1</td>
            <td>/models/1</td>
        </tr>
        <tr>
            <td>$\textcolor{crimson}{DELETE}$</td>
            <td>/cars/1</td>
            <td>/brands/1</td>
            <td>/models/1</td>
        </tr>
    </tbody>
</table>

## Rental Service

This is a simple rental service that allows you to create, read, update, and delete rentals.

<table>
    <thead>
        <tr>
            <th></th>
            <th>Rentals</th>
        </tr>
    </thead>
    <tbody>
<code>http://[host]:[port]/rental-service/api/v1</code>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/rentals</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/rentals/1</td>
        </tr>
        <tr>
            <td>$\textcolor{orange}{POST}$</td>
            <td>/rentals</td>
        </tr>
        <tr>
            <td>$\textcolor{violet}{PUT}$</td>
            <td>/rentals/1</td>
        </tr>
        <tr>
            <td>$\textcolor{crimson}{DELETE}$</td>
            <td>/rentals/1</td>
        </tr>
    </tbody>
</table>

## Filter Service

This is a simple filter service that allows you to filter cars by brand, model, and year.

<table>
    <thead>
        <tr>
            <th></th>
            <th>Filters</th>
        </tr>
    </thead>
    <tbody>
        <code>http://[host]:[port]/filter-service/api/v1</code>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/brand?brandName=porsche</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/model?modelName=e-200</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/plate?plate=61%20DC%2199</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/year?modelYear=2022</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/brand-search?brandName=Au</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/model-search?modelName=A</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/plate-search?plate=1</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/filters/state?state=1</td>
        </tr>
    </tbody>
</table>

## Payment Service

This is a simple payment service that allows you to create, read, update, and delete payments.

<table>
    <thead>
        <tr>
            <th></th>
            <th>Payments</th>
        </tr>
    </thead>
    <tbody>
    <code>http://[host]:[port]/payment-service/api/v1</code>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/payments</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/payments/1</td>
        </tr>
        <tr>
            <td>$\textcolor{orange}{POST}$</td>
            <td>/payments</td>
        </tr>
        <tr>
            <td>$\textcolor{violet}{PUT}$</td>
            <td>/payments/1</td>
        </tr>
        <tr>
            <td>$\textcolor{crimson}{DELETE}$</td>
            <td>/payments/1</td>
        </tr>
    </tbody>
</table>

## Invoice Service

This is a simple invoice service that allows you to create, read, update, and delete invoices.

<table>
    <thead>
        <tr>
            <th></th>
            <th>Invoices</th>
        </tr>
    </thead>
    <tbody>
    <code>http://[host]:[port]/invoice-service/api/v1</code>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/invoices</td>
        </tr>
        <tr>
            <td>$\textcolor{cornflowerblue}{GET}$</td>
            <td>/invoices/1</td>
        </tr>
        <tr>
            <td>$\textcolor{orange}{POST}$</td>
            <td>/invoices</td>
        </tr>
        <tr>
            <td>$\textcolor{violet}{PUT}$</td>
            <td>/invoices/1</td>
        </tr>
        <tr>
            <td>$\textcolor{crimson}{DELETE}$</td>
            <td>/invoices/1</td>
        </tr>
    </tbody>
</table>

## Used Technologies

```Zipkin,Sleuth,Maven,Docker,Lombok,Grafana,Swagger,Validation,MongoDB,Spring Boot,Spring Web,PostgreSQL,Prometheus,Spring Cloud,API Gateway,Apache Kafka,Spring Data JPA,Docker Compose,Spring Cloud Config,Spring Boot Actuator,Spring Boot Dev Tools,OpenFeign,Eureka Discovery Server/Client```
