package com.peter.elasticsearch.controller;

import com.peter.elasticsearch.model.Book;
import com.peter.elasticsearch.service.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private BookService bookService;

    @GetMapping("/save")
    public void save(){

        printElasticSearchInfo();

//        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
//        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
//        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

        //fuzzey search
        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 2));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

        books.forEach(x -> System.out.println(x));

    }

    //useful for debug
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch-->");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("<--ElasticSearch--");
    }
}
